package com.yanzhen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.eduservice.entity.EduChapter;
import com.yanzhen.eduservice.entity.EduVideo;
import com.yanzhen.eduservice.entity.chapter.ChapterVo;
import com.yanzhen.eduservice.entity.chapter.VideoVo;
import com.yanzhen.eduservice.mapper.EduChapterMapper;
import com.yanzhen.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanzhen.eduservice.service.EduVideoService;
import com.yanzhen.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoService.list(videoQueryWrapper);
        List<ChapterVo> res = new ArrayList<>();
        for (int i = 0; i < eduChapters.size(); i++) {
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapter = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapter);
            res.add(chapter);
            List<VideoVo> eduVideos1 = new ArrayList<>();
            for (int i1 = 0; i1 < eduVideos.size(); i1++) {
                EduVideo eduVideo1 = eduVideos.get(i1);
                if(eduVideo1.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo1,videoVo);
                    eduVideos1.add(videoVo);
                }
            }
            chapter.setChildren(eduVideos1);
        }
        return res;
    }

    @Override
    public boolean deleteById(String id) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",id);
        int count = eduVideoService.count(videoQueryWrapper);
        if(count > 0){
            throw  new GuliException(20001,"章节内有小节不能进行删除");
        }else {
            int i = baseMapper.deleteById(id);
            return i>0;
        }
    }

    @Override
    public void deleteByCourseId(String id) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        baseMapper.delete(chapterQueryWrapper);
    }
}
