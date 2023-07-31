package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstValueExpression;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpression;
import com.easy.query.core.expression.segment.scec.expression.ConstParamExpression;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.text.MessageFormat;

/**
 * create time 2023/6/16 20:55
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSQLNativeSegmentImpl {
    protected final QueryRuntimeContext runtimeContext;
    protected final String sqlSegment;
    protected final SQLNativeExpressionContext sqlConstExpressionContext;

    public AbstractSQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpressionContext sqlConstExpressionContext) {
        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }

    public TableAvailable getTable() {
        return null;
    }

    public String getPropertyName() {
        return null;
    }

    public String getAlias() {
        return sqlConstExpressionContext.getAlias();
    }


    public String toSQL(ToSQLContext toSQLContext) {

        String resultColumnConst = getResultSQL(toSQLContext);

        String alias = getAlias();
        if (alias != null) {
            return resultColumnConst + " AS " + alias;
        }
        return resultColumnConst;
    }
    private String getResultSQL(ToSQLContext toSQLContext){

        if(EasyCollectionUtil.isNotEmpty(sqlConstExpressionContext.getExpressions())){
            Object[] args = new Object[sqlConstExpressionContext.getExpressions().size()];
            for (int i = 0; i < sqlConstExpressionContext.getExpressions().size(); i++) {
                String arg = process(i, toSQLContext);
                args[i]=arg;
            }
            MessageFormat messageFormat = new MessageFormat(sqlSegment);
            return messageFormat.format(args);
        }
        return sqlSegment;
    }

    private String process(int index,ToSQLContext toSQLContext) {
        ConstParamExpression constParamExpression = sqlConstExpressionContext.getExpressions().get(index);
        if (constParamExpression instanceof ColumnPropertyExpression) {
            ColumnPropertyExpression columnPropertyExpression = (ColumnPropertyExpression) constParamExpression;
            return columnPropertyExpression.toSQL(runtimeContext, toSQLContext);

        } else if (constParamExpression instanceof ColumnConstValueExpression) {
            ColumnConstValueExpression columnConstValueExpression = (ColumnConstValueExpression) constParamExpression;
            columnConstValueExpression.addParams(toSQLContext);
            return "?";
        }
        throw new EasyQueryInvalidOperationException("can not process ConstParamExpression:" + EasyClassUtil.getInstanceSimpleName(constParamExpression));
    }

}