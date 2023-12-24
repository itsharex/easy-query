package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.ColumnBooleanFunctionAvailable;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionComparableBooleanChainExpression<T> extends ColumnFuncComparableExpression<T>,
        ColumnBooleanFunctionAvailable<T> {
}