package com.ling.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.R;
import com.ling.order.pojo.Order;
import com.ling.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Ling
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/eduorder/order")

public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,memberId);
        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据订单号查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfoById(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    /**
     * 根据课程Id和用户Id查询订单表中订单状态
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        return count>0;
    }
}

