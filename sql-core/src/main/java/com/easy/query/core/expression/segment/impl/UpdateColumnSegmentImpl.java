package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnPropertyPredicate.java
 * @Description: column和某个bean的值的断言节点
 * @Date: 2023/2/13 15:18
 * @author xuejiaming
 */
public class UpdateColumnSegmentImpl extends AbstractInsertUpdateSetColumnSQLSegmentImpl implements InsertUpdateSetColumnSQLSegment {

    public UpdateColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext){
        super(table,propertyName,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        PropertySQLParameter sqlParameter = new PropertySQLParameter(table, propertyName);
        return toSQLWithParameter(toSQLContext,sqlParameter);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,table,propertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSegmentImpl(table,propertyName,runtimeContext);
    }
}