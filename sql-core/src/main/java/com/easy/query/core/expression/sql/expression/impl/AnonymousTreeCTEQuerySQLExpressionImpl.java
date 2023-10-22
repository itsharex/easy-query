package com.easy.query.core.expression.sql.expression.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.AnonymousEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.List;

/**
 * create time 2023/4/22 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousTreeCTEQuerySQLExpressionImpl implements AnonymousEntityQuerySQLExpression {
    private final EntitySQLExpressionMetadata entitySQLExpressionMetadata;
    private final EntityQuerySQLExpression querySQLExpression;

    public AnonymousTreeCTEQuerySQLExpressionImpl(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityQuerySQLExpression querySQLExpression){
        this.entitySQLExpressionMetadata = entitySQLExpressionMetadata;

        this.querySQLExpression = querySQLExpression;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        StringBuilder sql = new StringBuilder("WITH RECURSIVE as_tree_cte AS (");
        String cteSQL = querySQLExpression.toSQL(toSQLContext);
        sql.append(cteSQL).append(") ");
        return sql.toString();
    }

    @Override
    public EntitySQLExpressionMetadata getExpressionMetadata() {
        return entitySQLExpressionMetadata;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return entitySQLExpressionMetadata.getRuntimeContext();
    }

    @Override
    public SQLBuilderSegment getProjects() {
        return null;
    }

    @Override
    public void setProjects(SQLBuilderSegment projects) {

    }

    @Override
    public PredicateSegment getWhere() {
        return null;
    }

    @Override
    public void setWhere(PredicateSegment where) {

    }

    @Override
    public SQLBuilderSegment getGroup() {
        return null;
    }

    @Override
    public void setGroup(SQLBuilderSegment group) {

    }

    @Override
    public PredicateSegment getHaving() {
        return null;
    }

    @Override
    public void setHaving(PredicateSegment having) {

    }

    @Override
    public SQLBuilderSegment getOrder() {
        return null;
    }

    @Override
    public void setOrder(SQLBuilderSegment order) {

    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public void setOffset(long offset) {

    }

    @Override
    public long getRows() {
        return 0;
    }

    @Override
    public void setRows(long rows) {

    }

    @Override
    public void setDistinct(boolean distinct) {

    }

    @Override
    public boolean isDistinct() {
        return false;
    }

    @Override
    public boolean hasLimit() {
        return false;
    }

    @Override
    public void setAllPredicate(PredicateSegment allPredicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PredicateSegment getAllPredicate() {
        throw new UnsupportedOperationException();
    }

//    @Override
//    public List<EntityQuerySQLExpression> getIncludes() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setIncludes(List<EntityQuerySQLExpression> includes) {
//        throw new UnsupportedOperationException();
//    }

    @Override
    public List<EntityTableSQLExpression> getTables() {
        return null;
    }

    @Override
    public EntityQuerySQLExpression cloneSQLExpression() {
        return new AnonymousTreeCTEQuerySQLExpressionImpl(entitySQLExpressionMetadata,querySQLExpression.cloneSQLExpression());
    }
}
