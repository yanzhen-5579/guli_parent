package com.yanzhen.eduservice.service;

import com.yanzhen.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanzhen.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteById(String id);

    void deleteByCourseId(String id);
}
