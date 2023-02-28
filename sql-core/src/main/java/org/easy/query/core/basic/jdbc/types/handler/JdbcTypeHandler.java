package org.easy.query.core.basic.jdbc.types.handler;

import org.easy.query.core.basic.jdbc.types.EasyParameter;
import org.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;

/**
 * @FileName: JdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 12:46
 * @Created by xuejiaming
 */
public interface JdbcTypeHandler {
    Object getValue(EasyResultSet resultSet) throws SQLException;
    void setParameter(EasyParameter parameter) throws SQLException;
    int getJdbcType();
}