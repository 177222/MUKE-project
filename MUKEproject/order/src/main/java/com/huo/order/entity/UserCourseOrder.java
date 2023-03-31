package com.huo.order.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user_course_order") // 用户课程订单表
public class UserCourseOrder implements Serializable {
    private static final long serialVersionUID = 777308790778683330L;
    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 用户id
     */
    private Object userId;
    /**
     * 课程id，根据订单中的课程类型来选择
     */
    private Object courseId;
    /**
     * 活动课程id
     */
    private Integer activityCourseId;
    /**
     * 订单来源类型: 1 用户下单购买 2 后台添加专栏
     */
    private Object sourceType;
    /**
     * 当前状态: 0已创建 10已支付 20已完成 30已取消 40已过期
     */
    private Object status;
    /**
     * 创建时间
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