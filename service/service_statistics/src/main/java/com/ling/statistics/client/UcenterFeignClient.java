package com.ling.statistics.client;

import com.ling.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author Ling
 * @date 2022/5/2 19:03
 */
@Component
public class UcenterFeignClient implements UcenterClient{
    @Override
    public R countRegister(String day) {
        return R.error();
    }
}
