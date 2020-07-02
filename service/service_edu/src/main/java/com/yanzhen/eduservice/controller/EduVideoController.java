package com.yanzhen.eduservice.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.client.VodClient;
import com.yanzhen.eduservice.entity.EduVideo;
import com.yanzhen.eduservice.service.EduVideoService;
import com.yanzhen.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/addeduvideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.removeAlyVideo(videoSourceId);
            if(r.getCode() == 20001){
                throw new GuliException(20001,"熔断器错误");
            }
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

}

