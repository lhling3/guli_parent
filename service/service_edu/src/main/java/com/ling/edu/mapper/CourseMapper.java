package com.ling.edu.mapper;

import com.ling.edu.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ling.edu.pojo.frontvo.CourseWebVo;
import com.ling.edu.pojo.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
