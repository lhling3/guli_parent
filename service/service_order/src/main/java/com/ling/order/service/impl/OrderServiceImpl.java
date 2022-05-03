package com.ling.order.service.impl;

import com.ling.commonutils.MemberVo;
import com.ling.commonutils.OrderCourseVo;
import com.ling.order.client.EduClient;
import com.ling.order.client.UcenterClient;
import com.ling.order.pojo.Order;
import com.ling.order.mapper.OrderMapper;
import com.ling.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.order.utils.OrderNoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Ling
 * @since 2022-04-30
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        //1、通过远程调用获取课程信息
        OrderCourseVo orderCourseVo = eduClient.getCourseInfoOrder(courseId);
        //2、通过远程调用获取用户信息
        MemberVo memberVo = ucenterClient.getMemberById(memberId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(orderCourseVo.getId());
        order.setCourseTitle(orderCourseVo.getTitle());
        order.setCourseCover(orderCourseVo.getCover());
        order.setTeacherName(orderCourseVo.getTeacherName());
        order.setMemberId(memberVo.getId());
        order.setNickname(memberVo.getNickname());
        order.setMobile(memberVo.getMobile());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
