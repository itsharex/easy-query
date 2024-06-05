package com.easy.query.test.entity.base.proxy;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.test.entity.base.City;
import com.easy.query.test.entity.base.Province;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 * 如果出现属性冲突请使用@ProxyProperty进行重命名
 *
 * @author easy-query
 */
public class ProvinceProxy extends AbstractProxyEntity<ProvinceProxy, Province> {

    private static final Class<Province> entityClass = Province.class;

    public static ProvinceProxy createTable() {
        return new ProvinceProxy();
    }

    public ProvinceProxy() {
    }

    /**
     * {@link Province#getCode}
     */
    public SQLStringTypeColumn<ProvinceProxy> code() {
        return getStringTypeColumn("code");
    }

    /**
     * {@link Province#getName}
     */
    public SQLStringTypeColumn<ProvinceProxy> name() {
        return getStringTypeColumn("name");
    }

    /**
     * {@link Province#getCities}
     */
    public SQLManyQueryable<ProvinceProxy,CityProxy, City> cities() {
        return getNavigateMany("cities", new com.easy.query.test.entity.base.proxy.CityProxy());
    }


    @Override
    public Class<Province> getEntityClass() {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public ProvinceProxyFetcher FETCHER = new ProvinceProxyFetcher(this, null, SQLSelectAsExpression.empty);


    public static class ProvinceProxyFetcher extends AbstractFetcher<ProvinceProxy, Province, ProvinceProxyFetcher> {

        public ProvinceProxyFetcher(ProvinceProxy proxy, ProvinceProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link Province#getCode}
         */
        public ProvinceProxyFetcher code() {
            return add(getProxy().code());
        }

        /**
         * {@link Province#getName}
         */
        public ProvinceProxyFetcher name() {
            return add(getProxy().name());
        }


        @Override
        protected ProvinceProxyFetcher createFetcher(ProvinceProxy cp, AbstractFetcher<ProvinceProxy, Province, ProvinceProxyFetcher> prev, SQLSelectAsExpression sqlSelectExpression) {
            return new ProvinceProxyFetcher(cp, this, sqlSelectExpression);
        }
    }

}
