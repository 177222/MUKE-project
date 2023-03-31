package com.huo.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huo.comment.entity.CourseComment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseCommentDao extends BaseMapper<CourseComment> {

    /**
     * 某个课程的全部留言（分页）
     * @param courseid 课程编号
     * @param offset 数据偏移
     * @param pageSize 每页条目数
     * @return 留言集合
     */
    @Select({"SELECT\n" +
            "        id,`course_id`,`section_id`,`lesson_id`,user_id,`user_name`,`parent_id`,`is_top`,`comment`,`like_count`,`is_reply`,`type`,`status`,create_time ,update_time ,is_del,`last_operator`,`is_notify`,`mark_belong`,`replied` \n" +
            "        FROM course_comment \n" +
            "        WHERE is_del = 0\n" +
            "        AND course_id = #{courseid}\n" +
            "        ORDER BY is_top DESC , like_count DESC , create_time DESC\n" +
            "        LIMIT #{offset}, #{pageSize}"})
    @Results({
            @Result(column = "id",property = "id"),//保留id
            @Result(column = "id" , property = "favoriteRecords", many = @Many(select = "com.huo.comment.mapper.CourseCommentFavoriteRecordDao.getFavorites"))//关联查询，调用另一个接口
    })
    List<CourseComment> getCommentsByCourseId(@Param("courseid") Integer courseid, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);



    /**
     * 更新点赞的数量
     * @param x +1的话，赞的数量增加，-1的话，赞的数量减少
     * @param comment_id 某条留言的编号
     * @return 0：保存失败，1：保存成功
     */
    @Update({"update course_comment set like_count = like_count + #{x} where id = #{comment_id}"})
    Integer updateLikeCount(@Param("x") Integer x, @Param("comment_id") Integer comment_id);
}