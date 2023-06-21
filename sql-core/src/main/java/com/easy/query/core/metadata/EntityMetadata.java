package com.easy.query.core.metadata;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraTableKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.annotation.Version;
import com.easy.query.core.basic.extension.conversion.DefaultValueConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.track.update.DefaultValueUpdateAtomicTrack;
import com.easy.query.core.basic.extension.track.update.ValueUpdateAtomicTrack;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.core.sharding.initializer.ShardingInitOption;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.router.table.TableUnit;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: EntityMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:17
 */
public class EntityMetadata {
    private final Class<?> entityClass;
    private String tableName;
    private String schema;

    public boolean isMultiTableMapping() {
        return shardingTablePropertyName != null;
    }

    public boolean isMultiDataSourceMapping() {
        return shardingDataSourcePropertyName != null;
    }

    private boolean columnValueUpdateAtomicTrack = false;

    private LogicDeleteMetadata logicDeleteMetadata;
    private VersionMetadata versionMetadata;
    private String shardingDataSourcePropertyName;
    private final Set<String> shardingDataSourcePropertyNames = new LinkedHashSet<>();
    private String shardingTablePropertyName;
    private final Set<String> shardingTablePropertyNames = new LinkedHashSet<>();
    private ShardingInitConfig shardingInitConfig;

    /**
     * 查询过滤器
     */
    private final List<PredicateFilterInterceptor> predicateFilterInterceptors = new ArrayList<>();
    private final List<EntityInterceptor> entityInterceptors = new ArrayList<>();
    private final List<UpdateSetInterceptor> updateSetInterceptors = new ArrayList<>();
    private final LinkedHashMap<String, ColumnMetadata> property2ColumnMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, NavigateMetadata> property2NavigateMap = new LinkedHashMap<>();
    private final Map<String/*property name*/, String/*column name*/> keyPropertiesMap = new LinkedHashMap<>();
    private final List<String/*column name*/> incrementColumns = new ArrayList<>(4);
    private final Map<String/*column name*/, ColumnMetadata> column2PropertyMap = new HashMap<>();

    private final Set<ActualTable> actualTables = new CopyOnWriteArraySet<>();
    private final Set<String> dataSources =new CopyOnWriteArraySet<>();
    private EntityMetadataTypeEnum entityMetadataType=EntityMetadataTypeEnum.BEAN;
    private Supplier<Object> beanConstructorCreator;
    public EntityMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void init(ServiceProvider serviceProvider) {

        if(Map.class.isAssignableFrom(entityClass)){
            entityMetadataType=EntityMetadataTypeEnum.MAP;
            return;
        }
        if(EasyClassUtil.isBasicType(entityClass)){
            entityMetadataType=EntityMetadataTypeEnum.BASIC_TYPE;
            return;
        }

        QueryConfiguration configuration = serviceProvider.getService(QueryConfiguration.class);
        JdbcTypeHandlerManager jdbcTypeHandlerManager = serviceProvider.getService(JdbcTypeHandlerManager.class);
        NameConversion nameConversion = configuration.getNameConversion();

        Table table = EasyClassUtil.getAnnotation(entityClass, Table.class);
        if (table != null) {
            this.tableName = nameConversion.convert(EasyStringUtil.defaultIfBank(table.value(), EasyClassUtil.getSimpleName(entityClass)));
            this.schema = table.schema();
        }
        HashSet<String> ignoreProperties = table != null ? new HashSet<>(Arrays.asList(table.ignoreProperties())) : new HashSet<>();

        List<Field> allFields = EasyClassUtil.getAllFields(this.entityClass);
        PropertyDescriptor[] ps = getPropertyDescriptor();
        int versionCount = 0;
        int logicDelCount = 0;
        FastBean fastBean = EasyBeanUtil.getFastBean(entityClass);
        this.beanConstructorCreator = fastBean.getBeanConstructorCreator();
        for (Field field : allFields) {
            String property = field.getName();
            if (ignoreProperties.contains(property)) {
                continue;
            }
            //未找到bean属性就直接忽略
            PropertyDescriptor propertyDescriptor = firstOrNull(ps, o -> Objects.equals(o.getName(), property));
            if (propertyDescriptor == null) {
                continue;
            }
            Navigate navigate = field.getAnnotation(Navigate.class);
            if (navigate != null) {
                String relationKey = navigate.value();
                RelationTypeEnum relationType = navigate.relationType();
                Class<?> navigateType = getNavigateType(relationType, field, propertyDescriptor);
                if(navigateType==null){
                    throw new EasyQueryInvalidOperationException("not found navigate type, property:["+property+"]");
                }
                NavigateMetadata navigateMetadata = new NavigateMetadata(this, property, navigateType, relationType, relationKey);
                property2NavigateMap.put(property,navigateMetadata);
                continue;
            }

            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            if (columnIgnore != null) {
                continue;
            }

            Column column = field.getAnnotation(Column.class);
            boolean hasColumnName = column != null && EasyStringUtil.isNotBlank(column.value());
            String columnName = hasColumnName ? column.value() : nameConversion.convert(property);
            ColumnOption columnOption = new ColumnOption(this, columnName);
//            if (column != null) {
//                columnMetadata.setNullable(column.nullable());
//            }
            columnOption.setProperty(propertyDescriptor);

            Encryption encryption = field.getAnnotation(Encryption.class);
            if (encryption != null) {
                Class<? extends EncryptionStrategy> strategy = encryption.strategy();
                EncryptionStrategy easyEncryptionStrategy = configuration.getEasyEncryptionStrategy(strategy);
                if (easyEncryptionStrategy == null) {
                    throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " Encryption strategy unknown");
                }
                columnOption.setEncryptionStrategy(easyEncryptionStrategy);
                columnOption.setSupportQueryLike(encryption.supportQueryLike());
            }
            if (column != null) {
                Class<? extends ValueConverter<?, ?>> conversionClass = column.conversion();
                if (!Objects.equals(DefaultValueConverter.class, conversionClass)) {
                    ValueConverter<?, ?> valueConverter = configuration.getValueConverter(conversionClass);
                    if (valueConverter == null) {
                        throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " conversion unknown");
                    }
                    columnOption.setValueConverter(valueConverter);
                }

            }

            if (EasyStringUtil.isNotBlank(tableName)) {

                if (column != null) {
                    if (column.primaryKey()) {
                        keyPropertiesMap.put(property, columnName);
                    }
                    columnOption.setPrimary(column.primaryKey());
                    if (column.increment()) {
                        incrementColumns.add(columnName);
                    }
                    columnOption.setIncrement(column.increment());

                    columnOption.setLarge(column.large());
//                    columnMetadata.setSelect(column.select());
//                    columnMetadata.setNullable(false);//如果为主键那么之前设置的nullable将无效


                    Class<? extends ValueUpdateAtomicTrack<?>> trackValueUpdateClass = column.valueUpdateAtomicTrack();
                    if (!Objects.equals(DefaultValueUpdateAtomicTrack.class, trackValueUpdateClass)) {
                        ValueUpdateAtomicTrack<?> trackValueUpdate = configuration.getValueUpdateAtomicTrack(trackValueUpdateClass);
                        if (trackValueUpdate == null) {
                            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " trackValueUpdate unknown");
                        }
                        columnOption.setValueUpdateAtomicTrack(EasyObjectUtil.typeCastNullable(trackValueUpdate));
                        columnValueUpdateAtomicTrack = true;
                    }
                }
                InsertIgnore insertIgnore = field.getAnnotation(InsertIgnore.class);
                if (insertIgnore != null) {
                    columnOption.setInsertIgnore(true);
                }

                UpdateIgnore updateIgnore = field.getAnnotation(UpdateIgnore.class);
                if (updateIgnore != null) {
                    columnOption.setUpdateIgnore(true);
                }
                Version version = field.getAnnotation(Version.class);
                if (version != null) {

                    Class<? extends VersionStrategy> strategy = version.strategy();
                    VersionStrategy easyVersionStrategy = configuration.getEasyVersionStrategyOrNull(strategy);
                    if (easyVersionStrategy == null) {
                        throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " Version strategy unknown");
                    }
                    columnOption.setVersion(true);

                    versionMetadata = new VersionMetadata(property, easyVersionStrategy);

                    versionCount++;
                }
                ShardingDataSourceKey shardingDataSourceKey = field.getAnnotation(ShardingDataSourceKey.class);
                if (shardingDataSourceKey != null) {
                    this.setShardingDataSourcePropertyName(property);
                }
                ShardingExtraDataSourceKey shardingExtraDataSourceKey = field.getAnnotation(ShardingExtraDataSourceKey.class);
                if (shardingExtraDataSourceKey != null) {
                    this.addExtraShardingDataSourcePropertyName(property);
                }
                ShardingTableKey shardingTableKey = field.getAnnotation(ShardingTableKey.class);
                if (shardingTableKey != null) {
                    this.setShardingTablePropertyName(property);
                }
                ShardingExtraTableKey shardingExtraTableKey = field.getAnnotation(ShardingExtraTableKey.class);
                if (shardingExtraTableKey != null) {
                    this.addExtraShardingTablePropertyName(property);
                }

                LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
                if (logicDelete != null) {
                    LogicDeleteStrategyEnum strategy = logicDelete.strategy();
                    if (Objects.equals(LogicDeleteStrategyEnum.CUSTOM, strategy)) {//使用自定义
                        String strategyName = logicDelete.strategyName();
                        if (EasyStringUtil.isBlank(strategyName)) {
                            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " logic delete strategy is empty");
                        }
                        LogicDeleteStrategy globalLogicDeleteStrategy = configuration.getLogicDeleteStrategyNotNull(strategyName);
                        LogicDeleteBuilder logicDeleteBuilder = new LogicDeleteBuilder(this, property, field.getType());
                        globalLogicDeleteStrategy.configure(logicDeleteBuilder);
                    } else {//使用系统默认的
                        LogicDeleteStrategy sysGlobalLogicDeleteStrategy = configuration.getSysLogicDeleteStrategyNotNull(strategy);
                        LogicDeleteBuilder logicDeleteBuilder = new LogicDeleteBuilder(this, property, field.getType());
                        sysGlobalLogicDeleteStrategy.configure(logicDeleteBuilder);
                    }
                    logicDelCount++;
                }
            }
            Property<Object, ?> beanGetter = fastBean.getBeanGetter(propertyDescriptor);
            columnOption.setGetterCaller(beanGetter);
            PropertySetterCaller<Object> beanSetter = fastBean.getBeanSetter(propertyDescriptor);
            columnOption.setSetterCaller(beanSetter);
            JdbcTypeHandler jdbcTypeHandler = jdbcTypeHandlerManager.getHandler(columnOption.getProperty().getPropertyType());
            columnOption.setJdbcTypeHandler(jdbcTypeHandler);
            ColumnMetadata columnMetadata = new ColumnMetadata(columnOption);
            property2ColumnMap.put(property, columnMetadata);
            column2PropertyMap.put(columnName, columnMetadata);
        }

        if (versionCount > 1) {
            throw new EasyQueryException("multi version not support");
        }
        if (logicDelCount > 1) {
            throw new EasyQueryException("multi logic delete not support");
        }
        //初始化拦截器
        entityGlobalInterceptorConfigurationInit(configuration);

        if (table != null && isSharding()) {
            Class<? extends ShardingInitializer> initializer = table.shardingInitializer();
            initSharding(configuration, initializer);
        }
    }

    private Class<?> getNavigateType(RelationTypeEnum relationType,Field field,PropertyDescriptor propertyDescriptor){

        if(relationType.equals(RelationTypeEnum.OneToMany)||relationType.equals(RelationTypeEnum.ManyToMany)){

            Type genericType = field.getGenericType();

            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                if (typeArguments.length > 0) {
                    Type elementType = typeArguments[0];
                    if (elementType instanceof Class) {
                        return (Class<?>) elementType;
                    }
                }
            }
            return null;
        }
        return propertyDescriptor.getPropertyType();
    }

    private PropertyDescriptor firstOrNull(PropertyDescriptor[] ps, Predicate<PropertyDescriptor> predicate) {
        for (PropertyDescriptor p : ps) {
            if (predicate.test(p)) {
                return p;
            }
        }
        return null;
    }

    private void initSharding(QueryConfiguration configuration, Class<? extends ShardingInitializer> initializer) {

        ShardingInitializer easyShardingInitializer = configuration.getEasyShardingInitializerOrNull(initializer);
        if (easyShardingInitializer == null) {
            throw new EasyQueryInvalidOperationException("not found sharding initializer:" + EasyClassUtil.getSimpleName(initializer));
        }
        ShardingEntityBuilder<Object> shardingInitializerBuilder = new ShardingEntityBuilder<Object>(this, configuration.getEasyQueryOption());
        easyShardingInitializer.initialize(shardingInitializerBuilder);
        ShardingInitOption shardingInitOption = shardingInitializerBuilder.build();
        Map<String, Collection<String>> initializeTables = shardingInitOption.getActualTableNames();
        if (initializeTables != null && !initializeTables.isEmpty()) {
            Set<String> dataSources = initializeTables.keySet();
            for (String dataSource : dataSources) {
                Collection<String> tableNames = initializeTables.get(dataSource);
                if (EasyCollectionUtil.isNotEmpty(tableNames)) {
                    for (String name : tableNames) {
                        addActualTableWithDataSource(dataSource, name);
                    }
                } else {
                    addActualTableWithDataSource(dataSource, tableName);
                }
            }
        }
        ShardingSequenceConfig shardingSequenceConfig = null;
        //如果有配置
        Comparator<TableUnit> defaultTableNameComparator = shardingInitOption.getDefaultTableNameComparator();
        if (defaultTableNameComparator != null) {

            shardingSequenceConfig = new ShardingSequenceConfig(defaultTableNameComparator
                    , shardingInitOption.getSequenceProperties()
                    , shardingInitOption.getMaxShardingQueryLimit()
                    , shardingInitOption.getSequenceCompareMethods()
                    , shardingInitOption.getSequenceCompareAscMethods()
                    , shardingInitOption.getSequenceLimitMethods()
                    , shardingInitOption.getSequenceConnectionModeMethods()
                    , shardingInitOption.getConnectionMode());

        }
        this.shardingInitConfig = new ShardingInitConfig(shardingInitOption.getReverseFactor(), shardingInitOption.getMinReverseTotal(), shardingSequenceConfig);
    }

    protected void entityGlobalInterceptorConfigurationInit(QueryConfiguration configuration) {

        if (EasyStringUtil.isNotBlank(tableName)) {
            List<Interceptor> globalInterceptors = configuration.getEasyInterceptors().stream().sorted(Comparator.comparingInt(Interceptor::order)).collect(Collectors.toList());
            for (Interceptor globalInterceptor : globalInterceptors) {
                if (globalInterceptor.apply(entityClass)) {
                    if (globalInterceptor instanceof PredicateFilterInterceptor) {
                        predicateFilterInterceptors.add((PredicateFilterInterceptor) globalInterceptor);
                    }
                    if (globalInterceptor instanceof EntityInterceptor) {
                        entityInterceptors.add((EntityInterceptor) globalInterceptor);
                    }
                    if (globalInterceptor instanceof UpdateSetInterceptor) {
                        updateSetInterceptors.add((UpdateSetInterceptor) globalInterceptor);
                    }
                }
            }
        }
    }

    private PropertyDescriptor[] getPropertyDescriptor() {
        try {
            return EasyClassUtil.propertyDescriptors(entityClass);
        } catch (IntrospectionException e) {
            throw new EasyQueryException(e);
        }
    }


    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getSchemaOrNull() {
        return schema;
    }

    public String getColumnName(String propertyName) {
        ColumnMetadata columnMetadata = property2ColumnMap.get(propertyName);
        if (columnMetadata == null) {
            throw new EasyQueryException(String.format("not found property:[%s] mapping column", propertyName));
        }
        return columnMetadata.getName();
    }

    public String getPropertyNameOrNull(String columnName) {
        return getPropertyNameOrNull(columnName, null);
    }
    public String getPropertyNameNotNull(String columnName) {
        String propertyName = getPropertyNameOrNull(columnName, null);

        if (propertyName == null) {
            throw new EasyQueryException(String.format("not found column:[%s] mapping property", columnName));
        }
        return propertyName;
    }

    /**
     * 忽略大小写
     *
     * @param columnName
     * @param def
     * @return
     */
    public String getPropertyNameOrNull(String columnName, String def) {
        ColumnMetadata columnMetadata = getColumnMetadataOrNull(columnName);
        if (columnMetadata == null) {
            return def;
        }
        return columnMetadata.getPropertyName();
    }
    public ColumnMetadata getColumnMetadataOrNull(String columnName) {
        ColumnMetadata columnMetadata=null;
        if(null==(columnMetadata=column2PropertyMap.get(columnName))){
            columnMetadata=column2PropertyMap.get(columnName.toLowerCase(Locale.ENGLISH));
        }
        return columnMetadata;

    }

    public Collection<ColumnMetadata> getColumns() {
        return property2ColumnMap.values();
    }

    public Collection<String> getProperties() {
        return property2ColumnMap.keySet();
    }

    public LinkedHashMap<String, ColumnMetadata> getProperty2ColumnMap() {
        return property2ColumnMap;
    }

    /**
     * 获取所有的key
     *
     * @return
     */
    public Collection<String> getKeyProperties() {
        return keyPropertiesMap.keySet();
    }

    public boolean isKeyProperty(String propertyName) {
        if (propertyName == null) {
            return false;
        }
        return keyPropertiesMap.containsKey(propertyName);
    }

    public NavigateMetadata getNavigateNotNull(String propertyName) {
        NavigateMetadata navigateMetadata = getNavigateOrNull(propertyName);
        if (navigateMetadata == null) {
            throw new EasyQueryException(String.format("not found property:[%s] mapping navigate", propertyName));
        }
        return navigateMetadata;
    }

    public NavigateMetadata getNavigateOrNull(String propertyName) {
        return property2NavigateMap.get(propertyName);
    }

    public ColumnMetadata getColumnNotNull(String propertyName) {
        ColumnMetadata columnMetadata = getColumnOrNull(propertyName);
        if (columnMetadata == null) {
            throw new EasyQueryException(String.format("not found property:[%s] mapping column name", propertyName));
        }
        return columnMetadata;
    }

    public ColumnMetadata getColumnOrNull(String propertyName) {
        return property2ColumnMap.get(propertyName);
    }

    public void checkTable() {
        if (EasyStringUtil.isEmpty(tableName)) {
            throw new EasyQueryException("current entity not mapping table name," + EasyClassUtil.getSimpleName(entityClass));
        }
    }

    public void setLogicDeleteMetadata(LogicDeleteMetadata logicDeleteMetadata) {
        this.logicDeleteMetadata = logicDeleteMetadata;
    }

    public LogicDeleteMetadata getLogicDeleteMetadata() {
        return logicDeleteMetadata;
    }

    /**
     * 是否启用逻辑删除
     *
     * @return
     */
    public boolean enableLogicDelete() {
        return logicDeleteMetadata != null;
    }


    public List<PredicateFilterInterceptor> getPredicateFilterInterceptors() {
        return predicateFilterInterceptors;
    }

    public List<EntityInterceptor> getEntityInterceptors() {
        return entityInterceptors;
    }

    public List<UpdateSetInterceptor> getUpdateSetInterceptors() {
        return updateSetInterceptors;
    }


    public List<String> getIncrementColumns() {
        return incrementColumns;
    }


    public boolean hasVersionColumn() {
        return versionMetadata != null;
    }

    public VersionMetadata getVersionMetadata() {
        return versionMetadata;
    }

    public boolean isSharding() {
        return isMultiTableMapping() || isMultiDataSourceMapping();
    }

    public String getShardingDataSourcePropertyName() {
        return shardingDataSourcePropertyName;
    }

    public void setShardingDataSourcePropertyName(String shardingDataSourcePropertyName) {
        if (shardingDataSourcePropertyNames.contains(shardingDataSourcePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding data source property name:[" + shardingDataSourcePropertyName + "]");
        }
        this.shardingDataSourcePropertyName = shardingDataSourcePropertyName;
        shardingDataSourcePropertyNames.add(shardingTablePropertyName);
    }

    public void addExtraShardingDataSourcePropertyName(String shardingExtraDataSourcePropertyName) {
        if (shardingDataSourcePropertyNames.contains(shardingExtraDataSourcePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding data source property name:[" + shardingExtraDataSourcePropertyName + "]");
        }
        shardingDataSourcePropertyNames.add(shardingExtraDataSourcePropertyName);
    }

    public String getShardingTablePropertyName() {
        return shardingTablePropertyName;
    }

    public void setShardingTablePropertyName(String shardingTablePropertyName) {
        if (shardingTablePropertyNames.contains(shardingTablePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding table property name:[" + shardingTablePropertyName + "]");
        }
        this.shardingTablePropertyName = shardingTablePropertyName;
        shardingTablePropertyNames.add(shardingTablePropertyName);
    }

    public void addExtraShardingTablePropertyName(String shardingExtraTablePropertyName) {
        if (shardingTablePropertyNames.contains(shardingExtraTablePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding table property name:[" + shardingExtraTablePropertyName + "]");
        }
        shardingTablePropertyNames.add(shardingExtraTablePropertyName);
    }

    public Collection<ActualTable> getActualTables() {
        return Collections.unmodifiableCollection(actualTables);
    }

    public void addActualTableWithDataSource(String dataSource, String actualTableName) {
        if (EasyStringUtil.isBlank(dataSource)) {
            throw new IllegalArgumentException("data source");
        }
        if (EasyStringUtil.isBlank(actualTableName)) {
            throw new IllegalArgumentException("actual table name");
        }
        dataSources.add(dataSource);
        actualTables.add(new ActualTable(dataSource,actualTableName));
    }

    public Collection<String> getDataSources() {
        return Collections.unmodifiableCollection(dataSources);
    }

    public Set<String> getShardingDataSourcePropertyNames() {
        return shardingDataSourcePropertyNames;
    }

    public Set<String> getShardingTablePropertyNames() {
        return shardingTablePropertyNames;
    }

    public ShardingInitConfig getShardingInitConfig() {
        return shardingInitConfig;
    }

    public boolean isColumnValueUpdateAtomicTrack() {
        return columnValueUpdateAtomicTrack;
    }

    public Supplier<Object> getBeanConstructorCreator() {
        return beanConstructorCreator;
    }

    public EntityMetadataTypeEnum getEntityMetadataType() {
        return entityMetadataType;
    }
}
