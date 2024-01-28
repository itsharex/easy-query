package com.easy.query.core.proxy.core.draft.proxy;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.TablePropColumn;

/**
 * create time 2024/1/26 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DraftProxy {
    <TProperty> void fetch(int index, PropTypeColumn<TProperty> column, TablePropColumn tablePropColumn);
    Class<?>[] getDraftPropTypes();
}
