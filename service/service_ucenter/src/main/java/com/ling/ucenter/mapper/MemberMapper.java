package com.ling.ucenter.mapper;

import com.ling.ucenter.pojo.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Ling
 * @since 2022-04-25
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegisterInDay(String day);
}
