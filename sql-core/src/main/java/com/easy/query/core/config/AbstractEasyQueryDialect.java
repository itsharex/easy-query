package com.easy.query.core.config;

/**
 * @FileName: DefaultEasyQueryDialect.java
 * @Description: 文件说明
 * @Date: 2023/2/12 09:22
 * @author xuejiaming
 */
public abstract class AbstractEasyQueryDialect implements EasyQueryDialect {
    protected abstract String getQuoteStart();

    protected abstract String getQuoteEnd();

    @Override
    public String getQuoteName(String keyword) {
        if (keyword.startsWith(getQuoteStart()) && keyword.endsWith(getQuoteEnd())) {
            return keyword;
        }
        return getQuoteStart() + keyword + getQuoteEnd();
    }
}
