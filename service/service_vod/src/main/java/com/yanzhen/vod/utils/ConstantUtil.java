package com.yanzhen.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantUtil implements InitializingBean {

    @Value("${aliyun.vod.keyid}")
    private String keyId;

    @Value("${aliyun.vod.keysecret}")
    private String keySecrert;

    public static String KEY_ID;
    public static String KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecrert;
    }
}
