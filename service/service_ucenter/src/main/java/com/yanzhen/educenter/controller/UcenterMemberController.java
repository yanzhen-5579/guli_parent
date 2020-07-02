package com.yanzhen.educenter.controller;


import com.yanzhen.commonutils.JwtUtils;
import com.yanzhen.commonutils.MD5;
import com.yanzhen.commonutils.R;
import com.yanzhen.commonutils.UcenterMemberOrder;
import com.yanzhen.educenter.entity.UcenterMember;
import com.yanzhen.educenter.entity.VO.RegisterVo;
import com.yanzhen.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.rmi.registry.RegistryHandler;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //有reguestbody必须使用post方式提交
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member){
        String token = ucenterMemberService.login(member);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("/getuserinfo")
    public R getUserInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userinfo",member);
    }

    @PostMapping("/getuserorder/{id}")
    public UcenterMemberOrder getUserOrder(@PathVariable String id){
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //统计每天注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = ucenterMemberService.countRegister(day);
        return R.ok().data("countregister",count);
    }
}

