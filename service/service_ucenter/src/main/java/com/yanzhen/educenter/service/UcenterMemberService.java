package com.yanzhen.educenter.service;

import com.yanzhen.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanzhen.educenter.entity.VO.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember selectByOpenid(String openid);

    Integer countRegister(String day);
}
