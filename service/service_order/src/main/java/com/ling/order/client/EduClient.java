package com.ling.order.client;

import com.ling.commonutils.OrderCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Ling
 * @date 2022/4/30 20:42
 */
@Component
@FeignClient(name = "service-edu")
public interface EduClient {
    @PostMapping("/eduservice/course/getCourseInfoOrder/{courseId}")
    public OrderCourseVo getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
