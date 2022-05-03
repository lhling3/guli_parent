package com.ling.order.service;

import com.ling.order.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Ling
 * @since 2022-04-30
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);
}
