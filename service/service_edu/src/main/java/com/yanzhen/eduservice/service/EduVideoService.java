package com.yanzhen.eduservice.service;

import com.yanzhen.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteByCourseId(String id);
}
