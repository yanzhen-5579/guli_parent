package com.yanzhen.educenter.controller;

import com.google.gson.Gson;
import com.yanzhen.commonutils.JwtUtils;
import com.yanzhen.educenter.entity.UcenterMember;
import com.yanzhen.educenter.service.UcenterMemberService;
import com.yanzhen.educenter.utils.ContantWxUtil;
import com.yanzhen.educenter.utils.HttpClientUtils;
import com.yanzhen.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxLoginController {
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @GetMapping("/callback")
    public String callback(String code,String state){
        try {
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessToken = String.format(baseAccessTokenUrl, ContantWxUtil.APP_ID, ContantWxUtil.APP_SECRET, code);
            String accessTokenInfo = HttpClientUtils.get(accessToken);
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)accessTokenMap.get("access_token");
            String openid = (String)accessTokenMap.get("openid");
            UcenterMember member = ucenterMemberService.selectByOpenid(openid);
            if(member == null){
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfo = String.format(baseUserInfoUrl, access_token, openid);
                userInfo = HttpClientUtils.get(userInfo);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                ucenterMemberService.save(member);
            }
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken;
        }catch (Exception e){
            throw new GuliException(20001,"登录失败了呢");
        }
    }

    @RequestMapping("/wxcode")
    public String login(){
        String url = null;
        try {
             url = URLEncoder.encode(ContantWxUtil.REDIRECT_URL, "utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        baseUrl = String.format(baseUrl, ContantWxUtil.APP_ID, url, "yzz");
        return "redirect:"+baseUrl;
    }
}
