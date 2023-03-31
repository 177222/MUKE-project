package com.huo.course.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@Table(name="course_media")
public class CourseMedia implements Serializable {
    private static final long serialVersionUID = -80134795424357484L;
    /**
    * 课程媒体主键ID
    */
    @Id
    private Integer id;
    /**
    * 课程Id
    */
    private Integer courseId;
    /**
    * 章ID
    */
    private Integer sectionId;
    /**
    * 课时ID
    */
    private Integer lessonId;
    /**
    * 封面图URL
    */
    private String coverImageUrl;
    /**
    * 时长（06:02）
    */
    private String duration;
    /**
    * 媒体资源文件对应的EDK
    */
    private String fileEdk;
    /**
    * 文件大小MB
    */
    private Object fileSize;
    /**
    * 文件名称
    */
    private String fileName;
    /**
    * 媒体资源文件对应的DK
    */
    private String fileDk;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 是否删除，0未删除，1删除
    */
    private Object isDel;
    /**
    * 时长，秒数（主要用于音频在H5控件中使用）
    */
    private Integer durationNum;
    /**
    * 媒体资源文件ID
    */
    private String fileId;
}