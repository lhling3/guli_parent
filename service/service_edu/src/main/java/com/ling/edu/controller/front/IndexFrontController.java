package com.ling.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.commonutils.R;
import com.ling.edu.pojo.Course;
import com.ling.edu.pojo.Teacher;
import com.ling.edu.service.CourseService;
import com.ling.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ling
 * @date 2022/4/24 15:28
 */
@RestController
@RequestMapping("/eduservice/indexfront")

public class IndexFrontController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public IndexFrontController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    /**
     * 查询前8条热门课程
     * 查询前4位名师
     */
    @GetMapping("index")
    public R index(){
        List<Course> courseList = courseService.listHotCourse();
        List<Teacher> teacherList = teacherService.listHotTeacher();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
