package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

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
    protected final SQLNativeExpression sqlNativeExpression;

    public AbstractSQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        this.runtimeContext = runtimeContext;
        this.sqlSegment = sqlSegment;
        this.sqlNativeExpression = sqlNativeExpression;
    }

    public TableAvailable getTable() {
        return null;
    }

    public String getPropertyName() {
        return null;
    }

    public String getAlias() {
        return sqlNativeExpression.getAlias();
    }


    public String toSQL(ToSQLContext toSQLContext) {

        String resultColumnConst = getResultSQL(toSQLContext);

        String alias = getAlias();
        if (alias != null) {
            return resultColumnConst + " AS " + EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
        }
        return resultColumnConst;
    }

    private String getResultSQL(ToSQLContext toSQLContext) {

        if (EasyCollectionUtil.isNotEmpty(sqlNativeExpression.getExpressions())) {
            Object[] args = new Object[sqlNativeExpression.getExpressions().size()];
            for (int i = 0; i < sqlNativeExpression.getExpressions().size(); i++) {
                ParamExpression paramExpression = sqlNativeExpression.getExpressions().get(i);
                Object arg = EasySQLExpressionUtil.parseParamExpression(runtimeContext, paramExpression, toSQLContext);
                args[i] = arg;
            }
            if (sqlNativeExpression.isKeepStyle()) {
                return MessageFormat.format(sqlSegment.replace("'", "''"), args);
            }
            return MessageFormat.format(sqlSegment, args);
        }
        return sqlSegment;
    }
}
