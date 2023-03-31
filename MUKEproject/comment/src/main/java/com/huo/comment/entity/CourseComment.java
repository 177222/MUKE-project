package com.huo.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 课程留言表(CourseComment)实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseComment implements Serializable {
    private static final long serialVersionUID = 922554392538715061L;
    // 一条留言：N个点赞
    private List<CourseCommentFavoriteRecord> favoriteRecords ;
    /**
    * 主键
    */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 课程id
    */
    private Integer courseId;
    /**
    * 章节id
    */
    private Integer sectionId;
    /**
    * 课时id
    */
    private Integer lessonId;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 运营设置用户昵称
    */
    private String userName;
    /**
    * 父级评论id
    */
    private Integer parentId;
    /**
    * 是否置顶：0不置顶，1置顶
    */
    private Object isTop=0;
    /**
    * 评论
    */
    private String comment;
    /**
    * 点赞数
    */
    private Integer likeCount=0;
    /**
    * 是否回复留言：0普通留言，1回复留言
    */
    private Object isReply=0;
    /**
    * 留言类型：0用户留言，1讲师留言，2运营马甲 3讲师回复 4小编回复 5官方客服回复
    */
    private Integer type;
    /**
    * 留言状态：0待审核，1审核通过，2审核不通过，3已删除
    */
    private Integer status=0;
    /**
    * 创建时间
    */
    private Date createTime=new Date();
    /**
    * 更新时间
    */
    private Date updateTime=new Date();
    /**
    * 是否删除
    */
    private Object isDel=0;
    /**
    * 最后操作者id
    */
    private Integer lastOperator;
    /**
    * 是否发送了通知
    */
    private Object isNotify=1;
    /**
    * 标记归属
    */
    private Object markBelong=0;
    /**
    * 回复状态 0 未回复 1 已回复
    */
    private Object replied=0;

}