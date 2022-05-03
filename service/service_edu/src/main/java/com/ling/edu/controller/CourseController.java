package com.ling.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.OrderCourseVo;
import com.ling.commonutils.R;
import com.ling.edu.pojo.Course;
import com.ling.edu.pojo.Teacher;
import com.ling.edu.pojo.frontvo.CourseWebVo;
import com.ling.edu.pojo.vo.CourseInfoVo;
import com.ling.edu.pojo.vo.CoursePublishVo;
import com.ling.edu.pojo.vo.CourseQuery;
import com.ling.edu.pojo.vo.TeacherQuery;
import com.ling.edu.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/eduservice/course")

public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * 添加课程基本信息
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    /**
     * 根据课程id得到课程信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    /**
     * 修改课程信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程id查询课程发布信息
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    /**
     * 修改课程状态，发布课程
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    /**
     * 课程列表
     */
    @GetMapping
    public R getCourseList(){
        List<Course> list = courseService.list(null);
        return R.ok().data("list",list);
    }
    /**
     * 课程列表条件查询带分页
     */
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R getCourseListWithConditionPage(@PathVariable("current") int  current, @PathVariable("limit") int limit,
                                            @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> coursePage = new Page<>(current,limit);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (courseQuery != null){
            //多条件组合查询，判断条件是否为空，不为空则拼接条件
            String title = courseQuery.getTitle();
            String status = courseQuery.getStatus();
            String teacherId = courseQuery.getTeacherId();
            String subjectParentId = courseQuery.getSubjectParentId();
            String subjectId = courseQuery.getSubjectId();

            if(!StringUtils.isEmpty(title)){
                //构建条件，模糊查询
                wrapper.like("title",title);
            }
            if(!StringUtils.isEmpty(status)){
                wrapper.eq("status",status);
            }
            if(!StringUtils.isEmpty(teacherId)){
                wrapper.ge("teacher_id",teacherId);
            }
            if(!StringUtils.isEmpty(subjectParentId)){
                wrapper.le("subject_parent_id",subjectParentId);
            }
            if(!StringUtils.isEmpty(subjectId)){
                wrapper.le("subject_id",subjectId);
            }
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        courseService.page(coursePage,wrapper);
        //总记录数
        long total = coursePage.getTotal();
        //数据list集合
        List<Course> records = coursePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    /**
     * 删除课程
     */
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourseById(courseId);
        return R.ok();
    }

    /**
     * 根据课程Id查询课程信息
     */
    @PostMapping("getCourseInfoOrder/{courseId}")
    public OrderCourseVo getCourseInfoOrder(@PathVariable String courseId){
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        OrderCourseVo orderCourseVo = new OrderCourseVo();
        BeanUtils.copyProperties(courseWebVo,orderCourseVo);
        return orderCourseVo;
    }

}

