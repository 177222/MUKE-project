package com.huo.course.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="activity_course")
public class ActivityCourse implements Serializable {
    private static final long serialVersionUID = -29054335318173039L;
    /**
    * 主键ID
    */
    @Id
    private Integer id;
    /**
    * 课程ID
    */
    private Integer courseId;
    /**
    * 活动开始时间
    */
    private Date beginTime;
    /**
    * 活动结束时间
    */
    private Date endTime;
    /**
    * 活动价格
    */
    private Object amount;
    /**
    * 库存值
    */
    private Integer stock;
    /**
    * 状态 0未上架 10已上架
    */
    private Object status;
    /**
    * 逻辑删除 0未删除 1删除
    */
    private Object isDel;
    /**
    * 备注
    */
    private String remark;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 创建人
    */
    private String createUser;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 更新人
    */
    private String updateUser;


}