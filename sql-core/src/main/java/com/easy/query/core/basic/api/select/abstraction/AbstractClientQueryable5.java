package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.basic.api.select.extension.queryable5.override.AbstractOverrideClientQueryable5;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 */
public abstract class AbstractClientQueryable5<T1, T2, T3, T4, T5> extends AbstractOverrideClientQueryable5<T1, T2, T3, T4, T5> implements ClientQueryable5<T1, T2, T3, T4, T5> {
    protected SQLExpressionProvider<T2> sqlExpressionProvider2;
    protected SQLExpressionProvider<T3> sqlExpressionProvider3;
    protected SQLExpressionProvider<T4> sqlExpressionProvider4;
    protected SQLExpressionProvider<T5> sqlExpressionProvider5;


    public AbstractClientQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, t3Class, t4Class, t5Class, sqlEntityExpression);
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> getClientQueryable5() {
        return this;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return this;
    }

    @Override
    public <T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> leftJoin(Class<T6> joinClass, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, t2Class, t3Class, t4Class, t5Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> leftJoin(ClientQueryable<T6> joinQueryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        ClientQueryable<T6> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, t2Class, t3Class, t4Class, t5Class, selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> rightJoin(Class<T6> joinClass, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, t2Class, t3Class, t4Class, t5Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> rightJoin(ClientQueryable<T6> joinQueryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        ClientQueryable<T6> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, t2Class, t3Class, t4Class, t5Class, selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> innerJoin(Class<T6> joinClass, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, t2Class, t3Class, t4Class, t5Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public <T6> ClientQueryable6<T1, T2, T3, T4, T5, T6> innerJoin(ClientQueryable<T6> joinQueryable, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> on) {
        ClientQueryable<T6> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable6(t1Class, t2Class, t3Class, t4Class,t5Class, selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable, on);
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> whereExpression) {
        if (condition) {
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate();
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate();
            WherePredicate<T3> sqlWherePredicate3 = getSQLExpressionProvider3().getWherePredicate();
            WherePredicate<T4> sqlWherePredicate4 = getSQLExpressionProvider4().getWherePredicate();
            WherePredicate<T5> sqlWherePredicate5 = getSQLExpressionProvider5().getWherePredicate();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2, sqlWherePredicate3, sqlWherePredicate4, sqlWherePredicate5);
        }
        return this;
    }

    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression5<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>> selectExpression) {

        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSQLExpressionProvider3().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T4, TR> sqlColumnAsSelector4 = getSQLExpressionProvider4().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T5, TR> sqlColumnAsSelector5 = getSQLExpressionProvider5().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2, sqlColumnAsSelector3, sqlColumnAsSelector4, sqlColumnAsSelector5);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    private <TMember> List<TMember> selectAggregateList(SQLExpression5<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>> columnSelectorExpression, ColumnFunction columnFunction, Class<TMember> resultClass) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T3> sqlColumnResultSelector3 = getSQLExpressionProvider3().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T4> sqlColumnResultSelector4 = getSQLExpressionProvider4().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T5> sqlColumnResultSelector5 = getSQLExpressionProvider5().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2, sqlColumnResultSelector3, sqlColumnResultSelector4, sqlColumnResultSelector5);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggreagate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment) projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        Class<TMember> tMemberClass = resultClass == null ? (Class<TMember>) table.getEntityMetadata().getColumnNotNull(propertyName).getPropertyType() : resultClass;
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, null);

        return cloneQueryable().select(funcColumnSegment, true).toList(tMemberClass);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression5<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>> columnSelectorExpression, BigDecimal def) {

        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression5<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>> columnSelectorExpression, TMember def) {
        ColumnFunction sumFunction = runtimeContext.getColumnFunctionFactory().createSumFunction(false);
        List<TMember> result = selectAggregateList(columnSelectorExpression, sumFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression5<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>> columnSelectorExpression, TMember def) {
        ColumnFunction maxFunction = runtimeContext.getColumnFunctionFactory().createMaxFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, maxFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression5<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>> columnSelectorExpression, TMember def) {
        ColumnFunction minFunction = runtimeContext.getColumnFunctionFactory().createMinFunction();
        List<TMember> result = selectAggregateList(columnSelectorExpression, minFunction, null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression5<ColumnResultSelector<T1>, ColumnResultSelector<T2>, ColumnResultSelector<T3>, ColumnResultSelector<T4>, ColumnResultSelector<T5>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        ColumnFunction avgFunction = runtimeContext.getColumnFunctionFactory().createAvgFunction(false);
        List<TResult> result = selectAggregateList(columnSelectorExpression, avgFunction, resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }


    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLExpression5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>> selectExpression) {
        if (condition) {
            ColumnGroupSelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            ColumnGroupSelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            ColumnGroupSelector<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getGroupColumnSelector();
            ColumnGroupSelector<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getGroupColumnSelector();
            ColumnGroupSelector<T5> sqlGroupSelector5 = getSQLExpressionProvider5().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4, sqlGroupSelector5);
        }
        return this;
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> having(boolean condition, SQLExpression5<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            WhereAggregatePredicate<T3> sqlGroupSelector3 = getSQLExpressionProvider3().getAggregatePredicate();
            WhereAggregatePredicate<T4> sqlGroupSelector4 = getSQLExpressionProvider4().getAggregatePredicate();
            WhereAggregatePredicate<T5> sqlGroupSelector5 = getSQLExpressionProvider5().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2, sqlGroupSelector3, sqlGroupSelector4, sqlGroupSelector5);
        }
        return this;
    }


    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(true);
            ColumnOrderSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(true);
            ColumnOrderSelector<T5> sqlOrderColumnSelector5 = getSQLExpressionProvider5().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4, sqlOrderColumnSelector5);
        }
        return this;
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            ColumnOrderSelector<T3> sqlOrderColumnSelector3 = getSQLExpressionProvider3().getOrderColumnSelector(false);
            ColumnOrderSelector<T4> sqlOrderColumnSelector4 = getSQLExpressionProvider4().getOrderColumnSelector(false);
            ColumnOrderSelector<T5> sqlOrderColumnSelector5 = getSQLExpressionProvider5().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2, sqlOrderColumnSelector3, sqlOrderColumnSelector4, sqlOrderColumnSelector5);
        }
        return this;
    }

    @Override
    public SQLExpressionProvider<T2> getSQLExpressionProvider2() {
        if (sqlExpressionProvider2 == null) {
            sqlExpressionProvider2 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(1, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider2;
    }

    @Override
    public SQLExpressionProvider<T3> getSQLExpressionProvider3() {
        if (sqlExpressionProvider3 == null) {
            sqlExpressionProvider3 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(2, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider3;
    }

    @Override
    public SQLExpressionProvider<T4> getSQLExpressionProvider4() {
        if (sqlExpressionProvider4 == null) {
            sqlExpressionProvider4 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(3, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider4;
    }

    @Override
    public SQLExpressionProvider<T5> getSQLExpressionProvider5() {
        if (sqlExpressionProvider5 == null) {
            sqlExpressionProvider5 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(4, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider5;
    }
}
