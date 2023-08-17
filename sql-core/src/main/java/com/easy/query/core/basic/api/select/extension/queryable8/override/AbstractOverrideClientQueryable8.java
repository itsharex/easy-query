package com.easy.query.core.basic.api.select.extension.queryable8.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected final Class<T4> t4Class;
    protected final Class<T5> t5Class;
    protected final Class<T6> t6Class;
    protected final Class<T7> t7Class;
    protected final Class<T8> t8Class;

    public AbstractOverrideClientQueryable8(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
        this.t5Class = t5Class;
        this.t6Class = t6Class;
        this.t7Class = t7Class;
        this.t8Class = t8Class;
    }

    protected abstract ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> getClientQueryable8();


    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable8();
    }

    @Override
    public <TProperty> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable8();
    }


    @Override
    public <TProperty> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTracking() {
        super.asTracking();
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable8();
    }


    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable8();
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTableLink(String linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable8();
    }
}