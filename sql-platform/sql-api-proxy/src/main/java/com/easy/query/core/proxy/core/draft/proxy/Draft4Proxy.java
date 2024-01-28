package com.easy.query.core.proxy.core.draft.proxy;

import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 *
 * @author xuejiaming
 */
public class Draft4Proxy<T1,T2,T3,T4> extends AbstractDraftProxy<Draft4Proxy<T1,T2,T3,T4>, Draft4<T1,T2,T3,T4>> {

    private static final Class<Draft4> entityClass = Draft4.class;

    public static <TR1,TR2,TR3,TR4> Draft4Proxy<TR1,TR2,TR3,TR4> createTable() {
        return new Draft4Proxy<>();
    }

    public Draft4Proxy() {
        super(4);
    }

    /**
     * {@link Draft4#getValue1}
     */
    public SQLAnyColumn<Draft4Proxy<T1,T2,T3,T4>, T1> value1() {
        return getAnyColumn("value1",null);
    }

    /**
     * {@link Draft4#getValue2()}
     */
    public SQLAnyColumn<Draft4Proxy<T1,T2,T3,T4>, T2> value2() {
        return getAnyColumn("value2",null);
    }
    /**
     * {@link Draft4#getValue3()}
     */
    public SQLAnyColumn<Draft4Proxy<T1,T2,T3,T4>, T3> value3() {
        return getAnyColumn("value3",null);
    }
    /**
     * {@link Draft4#getValue4()}
     */
    public SQLAnyColumn<Draft4Proxy<T1,T2,T3,T4>, T4> value4() {
        return getAnyColumn("value4",null);
    }


    @Override
    public Class<Draft4<T1,T2,T3,T4>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public Draft4ProxyFetcher<T1,T2,T3,T4> FETCHER = new Draft4ProxyFetcher<>(this, null, SQLSelectAsExpression.empty);


    public static class Draft4ProxyFetcher<TF1,TF2,TF3,TF4> extends AbstractFetcher<Draft4Proxy<TF1,TF2,TF3,TF4>, Draft4<TF1,TF2,TF3,TF4>, Draft4ProxyFetcher<TF1,TF2,TF3,TF4>> {

        public Draft4ProxyFetcher(Draft4Proxy<TF1,TF2,TF3,TF4> proxy, Draft4ProxyFetcher<TF1,TF2,TF3,TF4> prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link Draft4#getValue1}
         */
        public Draft4ProxyFetcher<TF1,TF2,TF3,TF4> value1() {
            return add(getProxy().value1());
        }


        /**
         * {@link Draft4#getValue2}
         */
        public Draft4ProxyFetcher<TF1,TF2,TF3,TF4> value2() {
            return add(getProxy().value2());
        }
        /**
         * {@link Draft4#getValue3}
         */
        public Draft4ProxyFetcher<TF1,TF2,TF3,TF4> value3() {
            return add(getProxy().value3());
        }
        /**
         * {@link Draft4#getValue4}
         */
        public Draft4ProxyFetcher<TF1,TF2,TF3,TF4> value4() {
            return add(getProxy().value4());
        }


        @Override
        protected Draft4ProxyFetcher<TF1,TF2,TF3,TF4> createFetcher(
                Draft4Proxy<TF1,TF2,TF3,TF4> cp,
                AbstractFetcher<Draft4Proxy<TF1,TF2,TF3,TF4>, Draft4<TF1,TF2,TF3,TF4>, Draft4ProxyFetcher<TF1,TF2,TF3,TF4>> prev,
                SQLSelectAsExpression sqlSelectExpression
        ) {
            return new Draft4ProxyFetcher<>(cp, this, sqlSelectExpression);
        }
    }

}
