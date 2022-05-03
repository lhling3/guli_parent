package com.ling.edu.controller;

import com.ling.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ling
 * @date 2022/4/11 19:43
 */
@RestController
@RequestMapping("/eduservice/user")

public class LoginController {
    /**
     * 登录接口
     */
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    /**
     * 获取用户信息
     */
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","admin").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
