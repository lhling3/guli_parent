package com.ling.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.edu.pojo.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.edu.pojo.frontvo.CourseFrontVo;
import com.ling.edu.pojo.frontvo.CourseWebVo;
import com.ling.edu.pojo.vo.CourseInfoVo;
import com.ling.edu.pojo.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoByCourseId(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);


    CoursePublishVo getPublishCourseInfo(String id);

    void removeCourseById(String courseId);

    List<Course> listHotCourse();

    Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
