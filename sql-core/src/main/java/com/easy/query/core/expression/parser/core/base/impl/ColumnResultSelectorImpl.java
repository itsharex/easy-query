package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * @author xuejiaming
 */
public class ColumnResultSelectorImpl<T1> implements ColumnResultSelector<T1> {
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final TableAvailable table;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public ColumnResultSelectorImpl(TableAvailable table, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {

        this.entityExpressionBuilder = entityExpressionBuilder;
        this.sqlSegmentFactory = entityExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
        this.table = table;
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public void column(String property) {
        if (sqlBuilderSegment.isNotEmpty()) {
            sqlBuilderSegment.getSQLSegments().clear();
        }
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        ColumnSegment columnSegment = sqlSegmentFactory.createSelectColumnSegment(table, columnMetadata, entityExpressionBuilder.getExpressionContext(), null);
        sqlBuilderSegment.append(columnSegment);

    }
}
