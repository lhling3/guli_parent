package com.ling.ucenter.service;

import com.ling.ucenter.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.ucenter.pojo.vo.LoginVo;
import com.ling.ucenter.pojo.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Ling
 * @since 2022-04-25
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegisterInDay(String day);
}
