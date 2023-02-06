package org.jdqc.sql.core.abstraction.sql;

import org.jdqc.sql.core.abstraction.sql.base.*;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 * @Created by xuejiaming
 */
public interface Select0<T1,TR,Chain> {
    int count();
    boolean any();
    TR firstOrNull();
    List<TR> toList();
    String toSql();
    Chain where(SqlExpression<WherePredicate<T1>> whereExpression);
    Chain select(SqlExpression<SqlSelector<T1,TR>> selectExpression);
   default Chain groupBy(SqlExpression<SqlColumnSelector<T1>> selectExpression){
       return groupBy(true,selectExpression);
   }
    Chain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);
    default Chain orderByAsc(SqlExpression<SqlColumnSelector<T1>> selectExpression){
        return orderByAsc(true,selectExpression);
    }
    Chain orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);
    default Chain orderByDesc(SqlExpression<SqlColumnSelector<T1>> selectExpression){
        return orderByDesc(true,selectExpression);
    }
    Chain orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression);
}
