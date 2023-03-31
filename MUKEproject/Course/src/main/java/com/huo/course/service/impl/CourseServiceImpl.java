package com.huo.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.huo.course.entity.*;
import com.huo.course.mapper.*;
import com.huo.course.remote.OrderRemoteService;
import com.huo.course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    //mybatis-plus不支撑多表查询，组合DTO实现多表数据
    @Override
    public List<CourseDTO> getAllCourse() {
        //将redis内存中的序列化的集合名称用String重新命名（增加可读性）
        RedisSerializer rs = new StringRedisSerializer();
        redisTemplate.setKeySerializer(rs);

        // 1、先去redis中查询
        System.out.println("***查询redis***");
        // 课程dto集合
        List<CourseDTO> courseDTOS = ( List<CourseDTO>)redisTemplate.opsForValue().get("allCourses");
        // 2、redis中没有，才回去mysql查询
        if(null == courseDTOS){
            synchronized (this) {
                courseDTOS = ( List<CourseDTO>)redisTemplate.opsForValue().get("allCourses");
                if(null == courseDTOS){
                    System.out.println("===查询mysql===");
                    // 查询全部课程
                    List<Course> courses = getInitCourse(null);
                    courseDTOS = new ArrayList<>();
                    for(Course course : courses){
                        CourseDTO dto = new CourseDTO();
                        // course将属性全部赋给给courseDTO对象
                        BeanUtils.copyProperties(course, dto);
                        courseDTOS.add(dto);
                        // 设置老师
                        setTeacher(dto);
                        // 设置前两节课
                        setTop2Lesson(dto);
                    }
                    redisTemplate.opsForValue().set("allCourses", courseDTOS, 10, TimeUnit.MINUTES);
                }
            }
        }
        return courseDTOS;
    }

    // 初始化基本的全部课程
    private List<Course> getInitCourse(List<Object> ids){
        QueryWrapper q = new QueryWrapper();
        q.eq("status", 1);// 已上架
        q.eq("is_del", Boolean.FALSE);// 未删除
        if(null != ids){
            q.in("id", ids); // where id in(1,2,3)
        }
        q.orderByDesc(" sort_num ");// 排序
        return this.courseMapper.selectList(q);
    }

    // 基本的老师查询
    private void setTeacher( CourseDTO courseDTO){
        QueryWrapper q = new QueryWrapper();
        q.eq("course_id", courseDTO.getId());// 一个课程，一个老师
        q.eq("is_del", Boolean.FALSE);// 未删除
        Teacher teacher = this.teacherMapper.selectOne(q);
        courseDTO.setTeacher(teacher);
    }
    // 前两节课
    private void setTop2Lesson( CourseDTO courseDTO ){
        QueryWrapper q = new QueryWrapper();
        q.eq("course_id", courseDTO.getId());// 一个课程，一个老师
        q.eq("is_del", Boolean.FALSE);// 未删除
        q.orderByAsc("section_id","order_num"); //排序
        q.last("limit 0, " + 2); // 只要前2条数据
        List<CourseLesson> list = this.lessonMapper.selectList(q);
        courseDTO.setLessonsDTO2(list);
    }

    @Override
    public CourseDTO getCourseById(Integer courseid) {
        // 根据课程id获取课程的基本信息
        Course course = this.courseMapper.selectOne(new QueryWrapper<Course>().eq("id", courseid));
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        // 关联老师
        setTeacher(courseDTO);
        // 关联章节
        List<SectionDTO> sectionDTOS = getCourseSection(courseDTO);
        courseDTO.setCourseSections(sectionDTOS);
        return courseDTO;
    }

    // 关联章节
    private List<SectionDTO> getCourseSection(CourseDTO courseDTO){
        QueryWrapper q = new QueryWrapper();
        q.eq("course_id", courseDTO.getId());// 一个课程，N章
        q.eq("is_del", Boolean.FALSE);// 未删除
        q.eq("status", 2);// 已发布
        q.orderByAsc("order_num"); //排序

        // 基本的章节集合
        List<CourseSection> list = this.sectionMapper.selectList(q);
        // 关联的章节集合
        List<SectionDTO> sectionDTOS = new ArrayList<>();
        for(CourseSection section : list){
            SectionDTO sectionDTO = new SectionDTO();
            BeanUtils.copyProperties(section, sectionDTO);

            q.clear(); // 清除条件
            q.eq("section_id", sectionDTO.getId());// 已发布
            q.eq("is_del", Boolean.FALSE);// 未删除
            q.orderByDesc("order_num"); //排序
            // 某章节的全部小节（基本信息）
            List<CourseLesson> lessons = this.lessonMapper.selectList(q);
            // 某章节的全部小节（关联信息）
            List<LessonDTO> lessonDTOS = new ArrayList<>();
            for(CourseLesson lesson : lessons){
                LessonDTO lessonDTO = new LessonDTO();
                BeanUtils.copyProperties(lesson, lessonDTO);
                // 设置每节课对应的视频
                setMedia(lessonDTO);
                lessonDTOS.add(lessonDTO);
            }
            // 章节关联所有小节
            sectionDTO.setCourseLessons(lessonDTOS);
            // 某个章节放入到章节集合
            sectionDTOS.add(sectionDTO);
        }
        return sectionDTOS;
    }

    // 设置每节课的视频
    private void setMedia(  LessonDTO lessonDTO ){
        QueryWrapper q = new QueryWrapper();
        q.eq("lesson_id", lessonDTO.getId());// 一节课，一个视频
        q.eq("is_del", Boolean.FALSE);// 未删除
        CourseMedia media = this.mediaMapper.selectOne(q);
        lessonDTO.setCourseMedia(media);
    }

    @Autowired
    private OrderRemoteService orderRemoteService;
    @Override
    public List<CourseDTO> getCoursesByUserId(Integer userid) {
        // 根据用户id获取已经购买的课程id集合
        List<Object> ids = orderRemoteService.getOKOrderCourseIds(userid);
        System.out.println("ids = " + ids);
        // 根据课程id集合，查询相应的课程信息集合
        return this.getMyCourses(ids);
    }

    // 查询已购买的课程
    private List<CourseDTO> getMyCourses(List<Object> ids) {
        //将redis内存中的序列化的集合名称用String重新命名（增加可读性）
        RedisSerializer rs = new StringRedisSerializer();
        redisTemplate.setKeySerializer(rs);

        // 1、先去redis中查询
        System.out.println("***查询redis***");
        // 课程dto集合
        List<CourseDTO> courseDTOS = ( List<CourseDTO>)redisTemplate.opsForValue().get("myCourses");
        // 2、redis中没有，才回去mysql查询
        if(null == courseDTOS){
            synchronized (this) {
                courseDTOS = ( List<CourseDTO>)redisTemplate.opsForValue().get("myCourses");
                if(null == courseDTOS){
                    System.out.println("===查询mysql===");
                    // 查询全部课程
                    List<Course> courses = getInitCourse(ids);
                    courseDTOS = new ArrayList<>();
                    for(Course course : courses){
                        CourseDTO dto = new CourseDTO();
                        // course将属性全部赋给给courseDTO对象
                        BeanUtils.copyProperties(course, dto);
                        courseDTOS.add(dto);
                        // 设置老师
                        setTeacher(dto);
                        // 设置前两节课
                        setTop2Lesson(dto);
                    }
                    redisTemplate.opsForValue().set("myCourses", courseDTOS, 10, TimeUnit.MINUTES);
                }
            }
        }
        return courseDTOS;
    }
}
