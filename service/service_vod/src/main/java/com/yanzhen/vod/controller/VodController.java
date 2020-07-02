package com.yanzhen.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yanzhen.commonutils.R;
import com.yanzhen.exceptionhandler.GuliException;
import com.yanzhen.vod.service.VodService;
import com.yanzhen.vod.utils.ConstantUtil;
import com.yanzhen.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vodservice/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadaly")
    public R uploadAlyVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }
    @DeleteMapping("/removealyvideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantUtil.KEY_ID,ConstantUtil.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw  new GuliException(20001,"删除视频失败");
        }
    }
    @DeleteMapping("/deletebatch")
    public R deleteBatch( @RequestParam("videoList") List<String> videoList){
        vodService.deleteBatch(videoList);
        return R.ok();
    }

    @GetMapping("/getplayauth/{id}")
    public R getPlayAuth(@PathVariable String id) throws Exception{
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantUtil.KEY_ID, ConstantUtil.KEY_SECRET);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(id);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        String playAuth = response.getPlayAuth();
        return R.ok().data("playAuth",playAuth);
    }
}
