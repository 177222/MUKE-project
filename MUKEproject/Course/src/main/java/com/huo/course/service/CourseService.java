package com.huo.course.service;

import com.huo.course.entity.CourseDTO;

import java.util.List;



public interface CourseService {
    /**
     * 查询全部
     * */
    List<CourseDTO> getAllCourse();
    /**
     * 查询某门课程的详细信息
     * @param courseid 课程编号
     * @return
     */
    CourseDTO getCourseById(Integer courseid);


    /**
     * 查询已登录用户购买的全部课程信息
     * @return
     */
    List<CourseDTO> getCoursesByUserId(Integer userid);


}
