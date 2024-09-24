package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.DSLRangeColumnFunctionPredicate;
import com.easy.query.core.proxy.predicate.DSLRangePredicate;
import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2023/12/15 17:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLRangeColumnFunctionAggregatePredicate<TProperty> extends DSLRangeColumnFunctionPredicate<TProperty>,DSLSQLFunctionAvailable {

    @Override
    default void rangeOpenClosed(boolean conditionLeft, PropTypeColumn<TProperty> valLeft, boolean conditionRight, PropTypeColumn<TProperty> valRight) {
        rangeFilter(getEntitySQLContext(),this, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN_CLOSED);
    }

    @Override
    default void rangeOpen(boolean conditionLeft, PropTypeColumn<TProperty> valLeft, boolean conditionRight, PropTypeColumn<TProperty> valRight) {
        rangeFilter(getEntitySQLContext(),this, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.OPEN);
    }

    @Override
    default void rangeClosedOpen(boolean conditionLeft, PropTypeColumn<TProperty> valLeft, boolean conditionRight, PropTypeColumn<TProperty> valRight) {
        rangeFilter(getEntitySQLContext(),this, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED_OPEN);
    }

    @Override
    default void rangeClosed(boolean conditionLeft, PropTypeColumn<TProperty> valLeft, boolean conditionRight, PropTypeColumn<TProperty> valRight) {
        rangeFilter(getEntitySQLContext(),this, conditionLeft, valLeft, conditionRight, valRight, SQLRangeEnum.CLOSED);
    }


    static <TProp> void rangeFilter(EntitySQLContext entitySQLContext, DSLSQLFunctionAvailable dslSQLFunction, boolean conditionLeft, PropTypeColumn<TProp> valLeft, boolean conditionRight, PropTypeColumn<TProp> valRight, SQLRangeEnum sqlRange) {
        if (conditionLeft && conditionRight) {
            entitySQLContext.accept(new SQLPredicateImpl(filter -> {
                filter.and(innerFilter -> {
                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                    rangeCompareFilter(innerFilter,dslSQLFunction, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE, valLeft);
                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                    rangeCompareFilter(innerFilter,dslSQLFunction, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE, valRight);
                });
            }));
        } else {
            if(conditionLeft){
                entitySQLContext.accept(new SQLPredicateImpl(filter -> {
                    boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                    rangeCompareFilter(filter,dslSQLFunction, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE, valLeft);
                }));
            }
            if(conditionRight){
                entitySQLContext.accept(new SQLPredicateImpl(filter -> {
                    boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                    rangeCompareFilter(filter,dslSQLFunction, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE, valRight);
                }));
            }
        }
    }

    static <TProp> void rangeCompareFilter(Filter filter, DSLSQLFunctionAvailable dslSQLFunction, SQLPredicateCompareEnum sqlPredicateCompare, PropTypeColumn<TProp> val){
        SQLFunc fx = filter.getRuntimeContext().fx();
        SQLFunction sqlFunction = dslSQLFunction.func().apply(fx);
        if (val instanceof SQLColumn) {
            filter.funcColumnFilter(dslSQLFunction.getTable(), sqlFunction, val.getTable(), val.getValue(), sqlPredicateCompare);
        } else if (val instanceof DSLSQLFunctionAvailable) {
            DSLSQLFunctionAvailable valLeftFunc = (DSLSQLFunctionAvailable) val;
            filter.funcColumnFuncFilter(dslSQLFunction.getTable(), sqlFunction, valLeftFunc.getTable(), valLeftFunc.func().apply(fx),sqlPredicateCompare);
        }else{
            throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(val));
        }
    }
}