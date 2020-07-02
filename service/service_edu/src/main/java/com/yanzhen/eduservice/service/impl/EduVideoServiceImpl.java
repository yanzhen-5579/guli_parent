package com.yanzhen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.eduservice.client.VodClient;
import com.yanzhen.eduservice.entity.EduVideo;
import com.yanzhen.eduservice.mapper.EduVideoMapper;
import com.yanzhen.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        queryWrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = null;
            if(eduVideo != null){
                 videoSourceId = eduVideo.getVideoSourceId();
            }
            if(!StringUtils.isEmpty(videoSourceId)){
                list.add(videoSourceId);
            }
        }

        if(list.size()>0){
            vodClient.deleteBatch(list);
        }
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        baseMapper.delete(videoQueryWrapper);
    }
}
