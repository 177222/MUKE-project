package com.huo.course.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Table(name="course")
public class CourseDTO implements Serializable {

    private Teacher teacher; // 一门课程对应一个讲师
    private List<CourseLesson> lessonsDTO2; // 一门课程关联前两节课时
    private List<SectionDTO> courseSections; //一门课程对应多个章节

    private static final long serialVersionUID = 282531706583365289L;
    /**
    * id
    */
    @Id
    private Object id;
    /**
    * 课程名
    */
    private String courseName;
    /**
    * 课程一句话简介
    */
    private String brief;
    /**
    * 原价
    */
    private Object price;
    /**
    * 原价标签
    */
    private String priceTag;
    /**
    * 优惠价
    */
    private Object discounts;
    /**
    * 优惠标签
    */
    private String discountsTag;
    /**
    * 描述markdown
    */
    private Object courseDescriptionMarkDown;
    /**
    * 课程描述
    */
    private Object courseDescription;
    /**
    * 课程分享图片url
    */
    private String courseImgUrl;
    /**
    * 是否新品
    */
    private Object isNew;
    /**
    * 广告语
    */
    private String isNewDes;
    /**
    * 最后操作者
    */
    private Integer lastOperatorId;
    /**
    * 自动上架时间
    */
    private Date autoOnlineTime;
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
    * 总时长(分钟)
    */
    private Integer totalDuration;
    /**
    * 课程列表展示图片
    */
    private String courseListImg;
    /**
    * 课程状态，0-草稿，1-上架
    */
    private Integer status;
    /**
    * 课程排序，用于后台保存草稿时用到
    */
    private Integer sortNum;
    /**
    * 课程预览第一个字段
    */
    private String previewFirstField;
    /**
    * 课程预览第二个字段
    */
    private String previewSecondField;
    /**
    * 销量
    */
    private Integer sales;

}