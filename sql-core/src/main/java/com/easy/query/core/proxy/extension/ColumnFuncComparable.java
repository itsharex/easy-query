package com.easy.query.core.proxy.extension;

import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLValueAggregatePredicate;

/**
 * create time 2023/12/3 09:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncComparable<T> extends ColumnComparable<T>,
        DSLValueAggregatePredicate<T> {
    @Override
    default SQLSelectAs as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(s -> {
            SQLFunc fx = s.getRuntimeContext().fx();
            s.columnFunc(this.getTable(), this.value(),func().apply(fx),propColumn.value(),()->{});
        });
    }
}