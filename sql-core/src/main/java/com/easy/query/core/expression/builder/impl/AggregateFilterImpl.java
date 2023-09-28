package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativePredicateImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

import java.util.Objects;

/**
 * create time 2023/6/23 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class AggregateFilterImpl implements AggregateFilter {
    private final QueryRuntimeContext runtimeContext;
    protected final PredicateSegment rootPredicateSegment;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected PredicateSegment nextPredicateSegment;

    public AggregateFilterImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, PredicateSegment predicateSegment) {
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;

        this.runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return this.runtimeContext;
    }

    @Override
    public AggregateFilter func(TableAvailable table, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val) {
        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(table, columnFunction, property, val, compare, runtimeContext));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(),runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment, sqlNativeExpressionContext));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter and() {
        this.nextPredicateSegment = new AndPredicateSegment();
        return this;
    }

    @Override
    public AggregateFilter and(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression) {
        this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
        AggregateFilterImpl aggregateFilter = new AggregateFilterImpl(entityQueryExpressionBuilder, this.nextPredicateSegment);
        aggregateFilterSQLExpression.apply(aggregateFilter);
        return this;
    }

    @Override
    public AggregateFilter or() {
        this.nextPredicateSegment = new OrPredicateSegment();
        return this;
    }

    @Override
    public AggregateFilter or(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression) {
        this.nextPredicateSegment = new OrPredicateSegment();
        this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
        AggregateFilterImpl aggregateFilter = new AggregateFilterImpl(entityQueryExpressionBuilder, this.nextPredicateSegment);
        aggregateFilterSQLExpression.apply(aggregateFilter);
        return this;
    }
}
