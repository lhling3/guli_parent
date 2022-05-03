package com.ling.edu.client;

import com.ling.commonutils.MemberVo;
import org.springframework.stereotype.Component;

/**
 * @author Ling
 * @date 2022/4/30 16:53
 */
@Component
public class UcenterFeignClient implements UcenterClient{
    @Override
    public MemberVo getMemberById(String id) {
        return null;
    }
}
