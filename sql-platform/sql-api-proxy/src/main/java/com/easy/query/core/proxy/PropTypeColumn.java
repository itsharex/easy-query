package com.easy.query.core.proxy;

/**
 * create time 2023/12/18 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropTypeColumn<TProperty> extends SQLSelectAsExpression,PropTypeAvailable{
    <TR> PropTypeColumn<TR> castType(Class<TR> clazz);
}