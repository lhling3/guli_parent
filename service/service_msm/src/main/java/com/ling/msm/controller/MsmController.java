package com.ling.msm.controller;

import com.ling.commonutils.R;
import com.ling.commonutils.RandomUtil;
import com.ling.commonutils.SendEmailUtil;
import com.ling.msm.service.MsmService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ling
 * @date 2022/4/24 20:35
 */
@RestController
@RequestMapping("/edumsm/msm")

public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信的方法
     */
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.send(phone, "SMS_180051135", param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }

    }

    /**
     * 发送邮件
     */
    @GetMapping("sendEmail/{userMail}")
    public R sendEmail(@PathVariable String userMail){
        String code = redisTemplate.opsForValue().get(userMail);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //生成随机值
        code = RandomUtil.getSixBitRandom();
        boolean isSend = SendEmailUtil.sendMessage(userMail,code);
        if(isSend) {
            redisTemplate.opsForValue().set(userMail, code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送邮件失败");
        }
    }


}
