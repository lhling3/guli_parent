package com.ling.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.edu.mapper.CourseDescriptionMapper;
import com.ling.edu.pojo.Course;
import com.ling.edu.mapper.CourseMapper;
import com.ling.edu.pojo.CourseDescription;
import com.ling.edu.pojo.frontvo.CourseFrontVo;
import com.ling.edu.pojo.frontvo.CourseWebVo;
import com.ling.edu.pojo.vo.CourseInfoVo;
import com.ling.edu.pojo.vo.CoursePublishVo;
import com.ling.edu.service.ChapterService;
import com.ling.edu.service.CourseDescriptionService;
import com.ling.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.edu.service.VideoService;
import com.ling.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    private final CourseDescriptionService courseDescriptionService;

    private final VideoService videoService;

    private final ChapterService chapterService;

    @Autowired
    public CourseServiceImpl(CourseDescriptionService courseDescriptionService, VideoService videoService, ChapterService chapterService) {
        this.courseDescriptionService = courseDescriptionService;
        this.videoService = videoService;
        this.chapterService = chapterService;
    }

    /**
     * 添加课程信息
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = baseMapper.insert(course);
        if(insert <= 0){
            //添加失败
            throw new GuliException(20001,"添加课程信息失败");
        }
        //获取添加课程之后的id
        String cid = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    /**
     * 根据课程Id查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfoByCourseId(String courseId) {
        //1、查询课程表
        Course course = baseMapper.selectById(courseId);
        //2、查询课程简介表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int update = baseMapper.updateById(course);
        if(update == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(id);
        return coursePublishVo;
    }

    /**
     * 删除课程
     * @param courseId
     */
    @Override
    public void removeCourseById(String courseId) {
        //1、根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);
        //2、根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);
        //3、根据课程id删除描述
        courseDescriptionService.removeById(courseId);
        //4、根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0){
            throw new GuliException(20001,"删除失败");
        }
    }

    /**
     * 查询前8热门课程
     * @return
     */
    @Cacheable(value = "course",key = "'selectHotCourse'")
    @Override
    public List<Course> listHotCourse() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<Course> courseList = baseMapper.selectList(courseQueryWrapper);
        return courseList;
    }

    /**
     * 前台条件分页查询课程
     * @param coursePage
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count",courseFrontVo.getBuyCountSort());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create",courseFrontVo.getGmtCreateSort());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price",courseFrontVo.getPriceSort());
        }
        baseMapper.selectPage(coursePage,wrapper);
        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("records", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
