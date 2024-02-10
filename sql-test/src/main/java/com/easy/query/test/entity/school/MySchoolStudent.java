package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.school.proxy.MySchoolStudentProxy;
import lombok.Data;
import lombok.ToString;

/**
 * create time 2023/7/16 11:28
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_school_student")
@Data
@ToString
//@EntityFileProxy
public class MySchoolStudent implements ProxyEntityAvailable<MySchoolStudent , MySchoolStudentProxy> {
    @Column(primaryKey = true)
    private String id;
    private String classId;
    private String name;
    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = "classId", targetProperty = "id")
    private MySchoolClass schoolClass;
    @Navigate(value = RelationTypeEnum.OneToOne, targetProperty = "studentId")
    private MySchoolStudentAddress schoolStudentAddress;

    @Override
    public Class<MySchoolStudentProxy> proxyTableClass() {
        return MySchoolStudentProxy.class;
    }
}
