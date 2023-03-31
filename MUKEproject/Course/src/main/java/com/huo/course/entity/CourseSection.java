package com.huo.course.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Table(name="course_section")
public class CourseSection implements Serializable {

    private static final long serialVersionUID = -27927452337172294L;
    /**
    * id
    */
    @Id
    private Object id;
    /**
    * 课程id
    */
    private Integer courseId;
    /**
    * 章节名
    */
    private String sectionName;
    /**
    * 章节描述
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
    /**
    * 排序字段
    */
    private Integer orderNum;
    /**
    * 状态，0:隐藏；1：待更新；2：已发布
    */
    private Integer status;



}