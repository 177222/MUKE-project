package com.huo.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huo.comment.entity.CourseComment;
import com.huo.comment.entity.CourseCommentFavoriteRecord;
import com.huo.comment.mapper.CourseCommentDao;
import com.huo.comment.mapper.CourseCommentFavoriteRecordDao;
import com.huo.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CourseCommentDao courseCommentDao;
    @Autowired
    private CourseCommentFavoriteRecordDao courseCommentFavoriteRecordDao;

    @Override
    public Integer saveComment(CourseComment comment) {
        return courseCommentDao.insert(comment);
    }

    @Override
    public List<CourseComment> getCommentsByCourseId(Integer courseid, Integer offset, Integer pageSize) {
        return courseCommentDao.getCommentsByCourseId(courseid,offset,pageSize);
    }

    @Override
    public Integer saveFavorite(Integer comment_id, Integer userid) {
        QueryWrapper<CourseCommentFavoriteRecord> qw = new QueryWrapper<>();
        qw.eq("comment_id", comment_id);
        qw.eq("user_id", userid);
        Integer i = courseCommentFavoriteRecordDao.selectCount(qw);
        int i1 = 0;
        int i2 = 0;
        CourseCommentFavoriteRecord favorite = new CourseCommentFavoriteRecord();
        favorite.setIsDel(0);

        if (i == 0) { //没点过赞
            // 添加赞的信息
            favorite.setCommentId(comment_id);
            favorite.setUserId(userid);
            favorite.setCreateTime(new Date());
            favorite.setUpdateTime(new Date());
            i1 = courseCommentFavoriteRecordDao.insert(favorite);
        } else {
            //更新favorite.IsDel为0，保持点赞状态
            i1 = courseCommentFavoriteRecordDao.update(favorite,qw);
        }

        //点赞数+1
        i2 = courseCommentDao.updateLikeCount(1, comment_id);


        if (i1 == 0 || i2 == 0) {
            throw new RuntimeException("点赞失败！");
        }
        return comment_id;
    }

    @Override
    public Integer cancelFavorite(Integer comment_id, Integer userid) {
        QueryWrapper<CourseCommentFavoriteRecord> qw = new QueryWrapper<>();
        qw.eq("comment_id", comment_id);
        qw.eq("user_id", userid);
        CourseCommentFavoriteRecord favorite = new CourseCommentFavoriteRecord();
        favorite.setIsDel(1); // 1 表示该赞被取消
        Integer i1 = courseCommentFavoriteRecordDao.update(favorite, qw);

        Integer i2 = courseCommentDao.updateLikeCount(-1, comment_id);

        if (i1 == 0 || i2 == 0) {
            throw new RuntimeException("取消赞失败！");
        }
        return i2;
    }
}
