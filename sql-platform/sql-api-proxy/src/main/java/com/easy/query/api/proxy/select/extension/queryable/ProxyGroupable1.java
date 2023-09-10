package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.extension.queryable.sql.MultiProxyGroupSelector1;
import com.easy.query.api.proxy.select.extension.queryable.sql.impl.MultiProxyGroupSelector1Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientProxyQueryableAvailable<T1>,ProxyQueryableAvailable<T1Proxy,T1>{

    default ProxyQueryable<T1Proxy, T1> groupBy(SQLExpression1<MultiProxyGroupSelector1<T1Proxy>> selectExpression) {
        return groupBy(true, selectExpression);
    }

   default ProxyQueryable<T1Proxy, T1> groupBy(boolean condition, SQLExpression1<MultiProxyGroupSelector1<T1Proxy>> selectExpression){
       if (condition) {
           getClientQueryable().groupBy(groupBySelector -> {
               selectExpression.apply(new MultiProxyGroupSelector1Impl<>(groupBySelector.getGroupSelector(), get1Proxy()));
           });
       }
       return getQueryable();
   }
}
