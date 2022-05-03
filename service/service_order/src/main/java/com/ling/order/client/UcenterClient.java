package com.ling.order.client;

import com.ling.commonutils.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Ling
 * @date 2022/4/30 20:42
 */
@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {
    @GetMapping("/educenter/member/getMemberById/{id}")
    public MemberVo getMemberById(@PathVariable("id") String id);
}
