package com.easy.query.test.entity.school.proxy;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.test.entity.school.SchoolClass;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 *
 * @author xuejiaming
 */
public class SchoolClassProxy extends AbstractProxyEntity < SchoolClassProxy, SchoolClass > {

    private static final Class < SchoolClass > entityClass = SchoolClass .class;

    public static SchoolClassProxy createTable () {
        return new SchoolClassProxy ();
    }

    public SchoolClassProxy () {
    }

    /**
     * {@link SchoolClass#getId}
     */
    public SQLColumn < SchoolClassProxy, java.lang.String> id(){
    return get("id");
}

    /**
     * {@link SchoolClass#getName}
     */
    public SQLColumn < SchoolClassProxy, java.lang.String> name(){
    return get("name");
}

    /**
     * 一对多 一个班级多个学生
     * {@link SchoolClass#getSchoolStudents}
     */
    public SQLNavigateColumn < SchoolClassProxy, com.easy.query.test.entity.school.SchoolStudent> schoolStudents(){
    return getNavigate("schoolStudents", com.easy.query.test.entity.school.SchoolStudent.class);
}

    /**
     * 中间表多对多配置,其中mappingClass表示中间表,selfMappingProperty表示中间表的哪个字段和当前表对应,
     * {@link SchoolClass#getSchoolTeachers}
     */
    public SQLNavigateColumn < SchoolClassProxy, com.easy.query.test.entity.school.SchoolTeacher> schoolTeachers(){
    return getNavigate("schoolTeachers", com.easy.query.test.entity.school.SchoolTeacher.class);
}


    @Override
    public Class < SchoolClass > getEntityClass () {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     * @return
     */
    public SchoolClassProxyFetcher FETCHER = new SchoolClassProxyFetcher (this, null, SQLSelectAsExpression.empty);


    public static class SchoolClassProxyFetcher extends AbstractFetcher<SchoolClassProxy, SchoolClass, SchoolClassProxyFetcher> {

        public SchoolClassProxyFetcher (SchoolClassProxy proxy, SchoolClassProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
        super(proxy, prev, sqlSelectAsExpression);
    }


        /**
         * {@link SchoolClass#getId}
         */
        public SchoolClassProxyFetcher id() {
            return add(getProxy().id());
        }

        /**
         * {@link SchoolClass#getName}
         */
        public SchoolClassProxyFetcher name() {
            return add(getProxy().name());
        }


        @Override
        protected SchoolClassProxyFetcher createFetcher(
            SchoolClassProxy cp,
            AbstractFetcher<SchoolClassProxy, SchoolClass, SchoolClassProxyFetcher> prev,
            SQLSelectAsExpression sqlSelectExpression
        ) {
            return new SchoolClassProxyFetcher (cp, this, sqlSelectExpression);
        }
    }

}