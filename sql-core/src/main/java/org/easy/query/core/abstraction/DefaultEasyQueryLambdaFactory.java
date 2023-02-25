package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.SqlAggregatePredicate;
import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.SqlPredicateContext;
import org.easy.query.core.impl.lambda.select.DefaultSqlAggregatePredicate;
import org.easy.query.core.impl.lambda.select.DefaultSqlColumnAsSelector;
import org.easy.query.core.impl.lambda.select.DefaultSqlColumnSelector;
import org.easy.query.core.impl.lambda.select.DefaultSqlPredicate;
import org.easy.query.core.segments.PredicateSegment;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory{
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlPredicateContext sqlPredicateContext, PredicateSegment predicateSegment) {
        return new DefaultSqlPredicate<>(index,sqlPredicateContext,predicateSegment);
    }

    @Override
    public <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlPredicateContext sqlPredicateContext, PredicateSegment predicateSegment) {
        return new DefaultSqlAggregatePredicate<>(index,sqlPredicateContext,predicateSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder) {
        return new DefaultSqlColumnSelector<>(index,selectContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,selectContext,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,selectContext,sqlSegmentBuilder);
    }
}
