package com.huo.course.controller;

import com.huo.course.entity.CourseDTO;
import com.huo.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("course")
@CrossOrigin //跨域
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("getAllCourse")
    public List<CourseDTO> getAllCourse() {

        // 模拟线程池，创建一个容量为20个线程的线程池

        ExecutorService es = Executors.newFixedThreadPool(20);
        for(int i = 1; i<=20; i++){
            es.submit(new Runnable() {
                @Override
                public void run() {
                    courseService.getAllCourse();
                }
            });
        }
        return courseService.getAllCourse();
    }

    @GetMapping("getCourseById/{courseid}")
    public CourseDTO getCourseById(@PathVariable("courseid")Integer courseid) {
        return courseService.getCourseById(courseid);
    }

    @GetMapping("getCoursesByUserId")
    public List<CourseDTO> getCoursesByUserId( Integer userid) {
        System.out.println("userid = " + userid);
        return courseService.getCoursesByUserId(userid);
    }
}