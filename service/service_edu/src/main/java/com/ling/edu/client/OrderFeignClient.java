package com.ling.edu.client;

import org.springframework.stereotype.Component;

/**
 * @author Ling
 * @date 2022/5/2 17:05
 */
@Component
public class OrderFeignClient implements OrderClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
