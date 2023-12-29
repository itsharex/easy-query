package com.easy.query.test.entity.school.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.test.entity.school.SchoolStudent;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 *
 * @author xuejiaming
 */
public class SchoolStudentProxy extends AbstractProxyEntity < SchoolStudentProxy, SchoolStudent > {

    private static final Class < SchoolStudent > entityClass = SchoolStudent .class;

    public static SchoolStudentProxy createTable () {
        return new SchoolStudentProxy ();
    }

    public SchoolStudentProxy () {
    }

    /**
     * {@link SchoolStudent#getId}
     */
    public SQLStringColumn < SchoolStudentProxy, java.lang.String> id(){
    return getStringColumn("id", java.lang.String.class);
}

    /**
     * {@link SchoolStudent#getClassId}
     */
    public SQLStringColumn < SchoolStudentProxy, java.lang.String> classId(){
    return getStringColumn("classId", java.lang.String.class);
}

    /**
     * {@link SchoolStudent#getName}
     */
    public SQLStringColumn < SchoolStudentProxy, java.lang.String> name(){
    return getStringColumn("name", java.lang.String.class);
}

    /**
     * {@link SchoolStudent#getSchoolClass}
     */
    public SQLNavigateColumn < SchoolStudentProxy, com.easy.query.test.entity.school.SchoolClass> schoolClass(){
    return getNavigate("schoolClass", com.easy.query.test.entity.school.SchoolClass.class);
}

    /**
     * {@link SchoolStudent#getSchoolStudentAddress}
     */
    public SQLNavigateColumn < SchoolStudentProxy, com.easy.query.test.entity.school.SchoolStudentAddress> schoolStudentAddress(){
    return getNavigate("schoolStudentAddress", com.easy.query.test.entity.school.SchoolStudentAddress.class);
}


    @Override
    public Class < SchoolStudent > getEntityClass () {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     * @return
     */
    public SchoolStudentProxyFetcher FETCHER = new SchoolStudentProxyFetcher (this, null, SQLSelectAsExpression.empty);


    public static class SchoolStudentProxyFetcher extends AbstractFetcher<SchoolStudentProxy, SchoolStudent, SchoolStudentProxyFetcher> {

        public SchoolStudentProxyFetcher (SchoolStudentProxy proxy, SchoolStudentProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
        super(proxy, prev, sqlSelectAsExpression);
    }


        /**
         * {@link SchoolStudent#getId}
         */
        public SchoolStudentProxyFetcher id() {
            return add(getProxy().id());
        }

        /**
         * {@link SchoolStudent#getClassId}
         */
        public SchoolStudentProxyFetcher classId() {
            return add(getProxy().classId());
        }

        /**
         * {@link SchoolStudent#getName}
         */
        public SchoolStudentProxyFetcher name() {
            return add(getProxy().name());
        }


        @Override
        protected SchoolStudentProxyFetcher createFetcher(
            SchoolStudentProxy cp,
            AbstractFetcher<SchoolStudentProxy, SchoolStudent, SchoolStudentProxyFetcher> prev,
            SQLSelectAsExpression sqlSelectExpression
        ) {
            return new SchoolStudentProxyFetcher (cp, this, sqlSelectExpression);
        }
    }

}
