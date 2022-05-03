package com.ling.ucenter.controller;


import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.R;
import com.ling.ucenter.pojo.Member;
import com.ling.ucenter.pojo.vo.LoginVo;
import com.ling.commonutils.MemberVo;
import com.ling.ucenter.pojo.vo.RegisterVo;
import com.ling.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Ling
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/educenter/member")

public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public R loginUser(@RequestBody LoginVo loginVo){
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据Token获取用户信息
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类方法获取用户Id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        return R.ok().data("member",member);
    }

    /**
     * 根据用户Id查询会员信息
     */
    @GetMapping("getMemberById/{id}")
    public MemberVo getMemberById(@PathVariable String id){
        Member member = memberService.getById(id);
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(member,memberVo);
        return memberVo;
    }

    /**
     * 查询某一天注册人数
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterInDay(day);
        System.out.println(count);
        return R.ok().data("count",count);
    }
}

