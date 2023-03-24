package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class ColumnInsertSegment implements SqlEntityAliasSegment {


    protected final SqlEntityTableExpression table;
    protected final String propertyName;
    protected final SqlEntityExpression sqlEntityExpression;
    protected String alias;

    public ColumnInsertSegment(SqlEntityTableExpression table, String propertyName, SqlEntityExpression sqlEntityExpression){
        this(table,propertyName,sqlEntityExpression,null);
    }
    public ColumnInsertSegment(SqlEntityTableExpression table, String propertyName, SqlEntityExpression sqlEntityExpression, String alias){
        this.table = table;

        this.propertyName = propertyName;
        this.sqlEntityExpression = sqlEntityExpression;
        this.alias = alias;
    }

    @Override
    public String toSql() {
        sqlEntityExpression.addParameter(new PropertySQLParameter(table,propertyName));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment);
        if(alias!=null){
            sql.append(" AS ").append(sqlEntityExpression.getQuoteName(alias));
        }
        return sql.toString();
    }

    @Override
    public SqlEntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getAlias() {
        return alias;
    }
}
