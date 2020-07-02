package com.yanzhen.eduservice.mapper;

import com.yanzhen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanzhen.eduservice.entity.frontvo.CourseWebVo;
import com.yanzhen.eduservice.entity.vo.PublishCourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    PublishCourseVo getPublishCourseVo(String id);

    CourseWebVo getFrontCourseInfo(String courseId);
}
