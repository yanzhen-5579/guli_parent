package com.yanzhen.eduservice.client;

import com.yanzhen.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除单个视频失败了");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频失败了");
    }
}
