package com.ling.edu.client;

import com.ling.commonutils.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Ling
 * @date 2022/4/30 16:52
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterFeignClient.class)
public interface UcenterClient {
    @GetMapping("/educenter/member/getMemberById/{id}")
    public MemberVo getMemberById(@PathVariable String id);
}
