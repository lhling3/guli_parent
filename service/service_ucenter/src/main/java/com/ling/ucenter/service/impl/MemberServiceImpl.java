package com.ling.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.MD5;
import com.ling.servicebase.exceptionhandler.GuliException;
import com.ling.ucenter.pojo.Member;
import com.ling.ucenter.mapper.MemberMapper;
import com.ling.ucenter.pojo.vo.LoginVo;
import com.ling.ucenter.pojo.vo.RegisterVo;
import com.ling.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Ling
 * @since 2022-04-25
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String email = loginVo.getEmail();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"邮箱和密码不能为空");
        }
        //判断邮箱是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Member emailMember = baseMapper.selectOne(wrapper);
        if(emailMember == null){
            throw new GuliException(20001,"邮箱不存在");
        }
        if(!MD5.encrypt(password).equals(emailMember.getPassword())){
            throw new GuliException(20001,"密码错误");
        }
        if(emailMember.getIsDisabled()){
            throw new GuliException(20001,"账户不能用，登录失败");
        }
        //登录成功
        String token = JwtUtils.getJwtToken(emailMember.getId(), emailMember.getNickname());

        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String email = registerVo.getEmail();
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //非空判断
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(code)
          || StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)
          || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"字段不能为空");
        }
        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if(!redisCode.equals(code)){
            throw new GuliException(20001,"验证码错误");
        }
        //判断邮箱是否重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"邮箱已存在");
        }
        //数据添加到数据库中
        Member member = new Member();
        BeanUtils.copyProperties(registerVo,member);
        member.setPassword(MD5.encrypt(registerVo.getPassword()));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterInDay(String day) {
        return baseMapper.countRegisterInDay(day);
    }
}
