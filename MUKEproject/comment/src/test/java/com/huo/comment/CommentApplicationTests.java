package com.huo.comment;

import com.huo.comment.entity.CourseComment;
import com.huo.comment.service.CommentService;
import com.huo.comment.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentApplicationTests {
    @Autowired
    public CommentServiceImpl csi;


    @Test
    void contextLoads() {
        CourseComment cc=new CourseComment();
        cc.setCourseId(13); // 课程编号
        cc.setSectionId(0); // 章节编号 （预留字段，为项目的2.0版本保留）
        cc.setLessonId(0);// 小节编号（预留字段，为项目的2.0版本保留）
        cc.setUserId(17722); // 用户编号
        cc.setUserName("huo"); // 用户昵称
        cc.setParentId(0); //没有父id（预留字段，为项目的2.0版本保留）
        cc.setComment("hello");// 留言内容
        cc.setType(0); // 0用户留言（预留字段，为项目的2.0版本保留）
        cc.setLastOperator(17722); //最后操作的用户编号
        System.out.println(csi.saveComment(cc));
    }

}
