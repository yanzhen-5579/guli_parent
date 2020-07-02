package com.yanzhen.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.commonutils.JwtUtils;
import com.yanzhen.commonutils.MD5;
import com.yanzhen.educenter.entity.UcenterMember;
import com.yanzhen.educenter.entity.VO.RegisterVo;
import com.yanzhen.educenter.mapper.UcenterMemberMapper;
import com.yanzhen.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanzhen.exceptionhandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String phone = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        UcenterMember mobileCenter = baseMapper.selectOne(wrapper);
        if(mobileCenter == null){
            throw new GuliException(20001,"登录失败");
        }
        if(!MD5.encrypt(password).equals(mobileCenter.getPassword())){
            throw new GuliException(20001,"登录失败");
        }
        if(mobileCenter.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }
        String token = JwtUtils.getJwtToken(mobileCenter.getId(), mobileCenter.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)||
        StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册用户不能为空");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0 ){
            throw new GuliException(20001,"手机号已存在");
        }

        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobileCode)){
            throw new GuliException(20001,"验证码错误");
        }
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember selectByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegister(String day) {

        return baseMapper.countRegister(day);
    }
}
