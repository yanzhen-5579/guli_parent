package com.yanzhen.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContantWxUtil implements InitializingBean {
    @Value("${wx.open.app_id}")
    private String appid;

    @Value("${wx.open.app_secret}")
    private String appSecret;

    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;
    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appid;
        APP_SECRET = appSecret;
        REDIRECT_URL = redirectUrl;
    }
}
