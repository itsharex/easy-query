package com.easy.query.sql.starter;

import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.DefaultDatabaseConfiguration;
import com.easy.query.core.bootstrapper.DefaultStarterConfigurer;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.bootstrapper.StarterConfigurer;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.DefaultNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.rule.datasource.DataSourceRouteRule;
import com.easy.query.core.sharding.rule.table.TableRouteRule;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.mssql.MsSQLDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.pgsql.PgSQLDatabaseConfiguration;
import com.easy.query.sql.starter.con.SpringConnectionManager;
import com.easy.query.sql.starter.con.SpringDataSourceUnitFactory;
import com.easy.query.sql.starter.config.EasyQueryInitializeOption;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import com.easy.query.sql.starter.logging.Slf4jImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author xuejiaming
 * @FileName: EasyQueryStarter.java
 * @Description: 文件说明
 * @Date: 2023/3/11 12:47
 */
@Configuration
@EnableConfigurationProperties(EasyQueryProperties.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@ConditionalOnProperty(
        prefix = "easy-query",
        value = {"enable"},
        matchIfMissing = true
)
public class EasyQueryStarterAutoConfiguration {
    private final DataSource dataSource;
    private final EasyQueryProperties easyQueryProperties;

    public EasyQueryStarterAutoConfiguration(DataSource dataSource,EasyQueryProperties easyQueryProperties) {
        this.dataSource = dataSource;
        this.easyQueryProperties = easyQueryProperties;
        if (EasyStringUtil.isBlank(easyQueryProperties.getLogClass())) {
            LogFactory.useCustomLogging(Slf4jImpl.class);
        } else {
            try {
                Class<?> aClass = Class.forName(easyQueryProperties.getLogClass());
                if (Log.class.isAssignableFrom(aClass)) {
                    LogFactory.useCustomLogging((Class<? extends Log>) aClass);
                } else {
                    LogFactory.useStdOutLogging();
                    System.out.println("cant found log:[" + easyQueryProperties.getLogClass() + "]!!!!!!");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("cant found log:[" + easyQueryProperties.getLogClass() + "]!!!!!!");
                e.printStackTrace();
            }
        }
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mysql")
    @ConditionalOnMissingBean
    public DatabaseConfiguration mysqlDatabaseConfiguration() {
        return new MySQLDatabaseConfiguration();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "h2")
    @ConditionalOnMissingBean
    public DatabaseConfiguration h2DatabaseConfiguration() {
        return new H2DatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mssql")
    @ConditionalOnMissingBean
    public DatabaseConfiguration mssqlDatabaseConfiguration() {
        return new MsSQLDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "pgsql")
    @ConditionalOnMissingBean
    public DatabaseConfiguration pgsqlDatabaseConfiguration() {
        return new PgSQLDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "default", matchIfMissing = true)
    @ConditionalOnMissingBean
    public DatabaseConfiguration databaseConfiguration() {
        return new DefaultDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.name-conversion", havingValue = "underlined", matchIfMissing = true)
    @ConditionalOnMissingBean
    public NameConversion underlinedNameConversion() {
        return new UnderlinedNameConversion();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.name-conversion", havingValue = "default")
    @ConditionalOnMissingBean
    public NameConversion defaultNameConversion() {
        return new DefaultNameConversion();
    }

    @Bean
    @ConditionalOnMissingBean
    public StarterConfigurer starterConfigurer() {
        return new DefaultStarterConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public EasyQueryClient easyQuery(DatabaseConfiguration databaseConfiguration, StarterConfigurer starterConfigurer, NameConversion nameConversion) {
        EasyQueryClient easyQuery = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .replaceService(DataSourceUnitFactory.class, SpringDataSourceUnitFactory.class)
                .optionConfigure(builder -> {
                    builder.setDeleteThrowError(easyQueryProperties.getDeleteThrow());
                    builder.setInsertStrategy(easyQueryProperties.getInsertStrategy());
                    builder.setUpdateStrategy(easyQueryProperties.getUpdateStrategy());
                    builder.setMaxShardingQueryLimit(easyQueryProperties.getMaxShardingQueryLimit());
                    builder.setExecutorMaximumPoolSize(easyQueryProperties.getExecutorMaximumPoolSize());
                    builder.setExecutorCorePoolSize(easyQueryProperties.getExecutorCorePoolSize());
                    builder.setThrowIfRouteNotMatch(easyQueryProperties.isThrowIfRouteNotMatch());
                    builder.setShardingExecuteTimeoutMillis(easyQueryProperties.getShardingExecuteTimeoutMillis());
                    builder.setQueryLargeColumn(easyQueryProperties.isQueryLargeColumn());
                    builder.setMaxShardingRouteCount(easyQueryProperties.getMaxShardingRouteCount());
                    builder.setExecutorQueueSize(easyQueryProperties.getExecutorQueueSize());
                    builder.setDefaultDataSourceName(easyQueryProperties.getDefaultDataSourceName());
                    builder.setDefaultDataSourceMergePoolSize(easyQueryProperties.getDefaultDataSourceMergePoolSize());
                    builder.setMultiConnWaitTimeoutMillis(easyQueryProperties.getMultiConnWaitTimeoutMillis());
                    builder.setWarningBusy(easyQueryProperties.isWarningBusy());
                })
                .replaceService(NameConversion.class, nameConversion)
                .replaceService(ConnectionManager.class, SpringConnectionManager.class)
                .useDatabaseConfigure(databaseConfiguration)
                .useStarterConfigure(starterConfigurer)
                .build();
        return easyQuery;
    }

    @Bean
    @ConditionalOnMissingBean
    public EasyQuery easyQuery(EasyQueryClient easyQueryClient) {
        return new DefaultEasyQuery(easyQueryClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public EasyQueryInitializeOption easyQueryInitializeOption(Map<String, Interceptor> interceptorMap,
                                                               Map<String, VersionStrategy> versionStrategyMap,
                                                               Map<String, LogicDeleteStrategy> logicDeleteStrategyMap,
                                                               Map<String, ShardingInitializer> shardingInitializerMap,
                                                               Map<String, EncryptionStrategy> encryptionStrategyMap,
                                                               Map<String, ValueConverter<?, ?>> valueConverterMap,
                                                               Map<String, TableRouteRule<?>> tableRouteRuleMap,
                                                               Map<String, DataSourceRouteRule<?>> dataSourceRouteRuleMap) {
        return new EasyQueryInitializeOption(interceptorMap, versionStrategyMap, logicDeleteStrategyMap, shardingInitializerMap, encryptionStrategyMap, valueConverterMap,tableRouteRuleMap,dataSourceRouteRuleMap);
    }
}