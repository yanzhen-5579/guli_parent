package com.yanzhen.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yanzhen.msmservice.service.MsmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map map, String phone) {
        if(StringUtils.isEmpty(phone)){
            return  false;
        }
        DefaultProfile profile = DefaultProfile.getProfile("default","LTAI4G9wdoEFXibj4HGPxLdT","peI18rdUpxHdqOSB9XEYcTYPFd5E9n");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName","门三雨辰在线教育门户");
        request.putQueryParameter("TemplateCode","SMS_193245023");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            return commonResponse.getHttpResponse().isSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
