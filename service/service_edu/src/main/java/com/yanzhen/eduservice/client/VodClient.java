package com.yanzhen.eduservice.client;

import com.yanzhen.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/vodservice/video/removealyvideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);
    @DeleteMapping("/vodservice/video/deletebatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
