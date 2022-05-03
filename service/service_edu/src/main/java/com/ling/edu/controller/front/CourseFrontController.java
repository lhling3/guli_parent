package com.ling.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.R;
import com.ling.edu.client.OrderClient;
import com.ling.edu.pojo.Course;
import com.ling.edu.pojo.chapter.ChapterVo;
import com.ling.edu.pojo.frontvo.CourseFrontVo;
import com.ling.edu.pojo.frontvo.CourseWebVo;
import com.ling.edu.service.ChapterService;
import com.ling.edu.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Ling
 * @date 2022/4/29 19:58
 */
@RestController
@RequestMapping("/eduservice/coursefront")

public class CourseFrontController {
    private final CourseService courseService;
    private final ChapterService chapterService;
    private final OrderClient orderClient;

    @Autowired
    public CourseFrontController(CourseService courseService, ChapterService chapterService, OrderClient orderClient) {
        this.courseService = courseService;
        this.chapterService = chapterService;
        this.orderClient = orderClient;
    }


    /**
     * 条件查询带分页查询课程
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<Course> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage,courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * 课程详情页方法
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //1、根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //2、根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //3、根据用户id和课程id查询是否购买了课程
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, memberId);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuyCourse",isBuyCourse);
    }
}
