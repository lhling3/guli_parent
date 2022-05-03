package com.ling.ucenter.controller;

import com.google.gson.Gson;
import com.ling.commonutils.JwtUtils;
import com.ling.servicebase.exceptionhandler.GuliException;
import com.ling.ucenter.pojo.Member;
import com.ling.ucenter.service.MemberService;
import com.ling.ucenter.util.ConstantPropertiesUtil;
import com.ling.ucenter.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Ling
 * @date 2022/4/26 21:15
 */
@Controller
@RequestMapping("/api/ucenter/wx")

public class WxApiController {
    @Autowired
    private MemberService memberService;
    /**
     * 生成微信扫描的二维码
     */
    @GetMapping("login")
    public String getWxCode(){
        //1、请求微信地址，获取code
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "ling"
        );

        return "redirect:" + url;
    }
    /**
     * 授权回调函数
     * 获取扫码人信息
     */
    @GetMapping("callback")
    public String callback(String code, String state){
        System.out.println("code:" + code);
        System.out.println("state:" +  state);
        try {
            //2、使用code，appid，secret向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code
            );
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo"+accessTokenInfo);
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //判断数据表里是否存在相同的信息，根据openid判断
            Member member = memberService.getOpenIdMember(openid);
            if(member == null){
                //3、拿着access_token和openid获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(userInfo);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                //把扫码人信息添加进数据库
                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+token;
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }
    }
}
