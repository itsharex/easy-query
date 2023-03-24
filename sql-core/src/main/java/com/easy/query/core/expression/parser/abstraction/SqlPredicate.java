package com.easy.query.core.expression.parser.abstraction;

import com.easy.query.core.expression.parser.abstraction.internal.WherePredicate;

/**
 *
 * @FileName: SqlPredicate.java
 * @Description: sql条件
 * @Date: 2023/2/5 15:38
 * @author xuejiaming
 */
public interface SqlPredicate<T1> extends WherePredicate<T1, SqlPredicate<T1>> {
}
