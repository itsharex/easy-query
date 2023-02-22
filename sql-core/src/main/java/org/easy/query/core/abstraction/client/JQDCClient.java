package org.easy.query.core.abstraction.client;

import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.sql.Select1;
import org.easy.query.core.basic.jdbc.Transaction;

/**
 *
 * @FileName: JQDCClient.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:27
 * @Created by xuejiaming
 */
public interface JQDCClient {
    EasyQueryRuntimeContext getRuntimeContext();
    <T1> Select1<T1> select(Class<T1> clazz);
    <T1> Select1<T1> select(Class<T1> clazz,String alias);
    default Transaction beginTransaction(){
        return beginTransaction(null);
    }
    Transaction beginTransaction(Integer isolationLevel);
}