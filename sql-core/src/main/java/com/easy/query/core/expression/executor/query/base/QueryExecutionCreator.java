package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/4/25 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryExecutionCreator extends BaseExecutionCreator{

    private final String dataSource;
    private final EasySqlExpression easySqlExpression;

    public QueryExecutionCreator(String dataSource, EasySqlExpression easySqlExpression) {
        this.dataSource = dataSource;
        this.easySqlExpression = easySqlExpression;
    }

    @Override
    protected Collection<ExecutionUnit> createExecutionUnits() {
        ExecutionUnit executionUnit = createExecutionUnit(dataSource, easySqlExpression, null, false);
        return Collections.singleton(executionUnit);
    }
}