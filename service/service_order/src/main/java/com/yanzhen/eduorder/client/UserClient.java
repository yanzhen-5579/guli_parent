package com.yanzhen.eduorder.client;


import com.yanzhen.commonutils.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UserClient {
    @PostMapping("/educenter/ucenter-member/getuserorder/{id}")
    public UcenterMemberOrder getUserOrder(@PathVariable("id") String id);
}
