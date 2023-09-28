package com.easy.query.api.proxy.sql.scec;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.text.MessageFormat;

/**
 * create time 2023/9/28 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeProxyExpressionChain<TChain> {
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> TChain expression(SQLColumn<TEntityProxy, TProperty> sqlColumn);

    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> TChain expression(ProxyQueryable<TEntityProxy, TEntity> subQuery);

    TChain value(Object val);

    TChain format(Object formatVal);

    TChain setAlias(String alias);
    /**
     * 因为默认{@link MessageFormat#format(Object)}会将参数"ifnull(id,'')"改为"ifnull(id,')"
     * 会认为单引号是转义符所以这边需要将单引号全部改为双引号,所以采用和输入文本一样的风格
     * 如果需要参数化将文本单引号改成双引号
     * @return
     */
    TChain keepStyle();

    /**
     * 使用message format 格式化风格 单引号作为转义
     * @return
     */
    TChain messageFormat();
}
