package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LongProxy extends AbstractBasicProxyEntity<LongProxy, Long> {
    public static LongProxy createTable() {
        return new LongProxy();
    }
    private static final Class<Long> entityClass = Long.class;

    private LongProxy() {
    }
    public LongProxy(Long val) {
        set(val);
    }


    public LongProxy(PropTypeColumn<Long> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Long> getEntityClass() {
        return entityClass;
    }
}