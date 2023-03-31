package com.huo.course.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="teacher")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 839525145260231267L;
    /**
    * id
    */
    @Id
    private Object id;
    /**
    * 课程ID
    */
    private Integer courseId;
    /**
    * 讲师姓名
    */
    private String teacherName;
    /**
    * 职务
    */
    private String position;
    /**
    * 讲师介绍
    */
    private String description;
    /**
    * 记录创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 是否删除
    */
    private Object isDel;

}