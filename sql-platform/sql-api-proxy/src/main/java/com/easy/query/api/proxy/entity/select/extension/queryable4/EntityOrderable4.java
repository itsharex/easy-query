package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.api.proxy.sql.impl.ProxyOrderSelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLOrderByExpression;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityOrderable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderBy(SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLOrderByExpression> selectExpression) {
        return orderBy(true, selectExpression);
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderBy(boolean condition, SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy, SQLOrderByExpression> selectExpression) {
        if (condition) {
            getClientQueryable4().orderByAsc((t, t1, t2, t3) -> {
                SQLOrderByExpression sqlOrderByExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy());
                sqlOrderByExpression.accept(t.getOrderSelector());
            });
        }
        return getQueryable4();
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByMerge(SQLFuncExpression1<Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>,SQLOrderByExpression> selectExpression) {
        return orderByMerge(true, selectExpression);
    }

    default EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> orderByMerge(boolean condition, SQLFuncExpression1<Tuple4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>,SQLOrderByExpression> selectExpression) {
        return orderBy(condition, (t, t1, t2, t3) -> {
            return selectExpression.apply(new Tuple4<>(t, t1, t2, t3));
        });
    }
}
