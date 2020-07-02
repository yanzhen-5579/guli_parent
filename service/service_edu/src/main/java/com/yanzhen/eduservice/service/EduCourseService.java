package com.yanzhen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanzhen.eduservice.entity.frontvo.CourseFrontVo;
import com.yanzhen.eduservice.entity.frontvo.CourseWebVo;
import com.yanzhen.eduservice.entity.vo.CourseInfoVo;
import com.yanzhen.eduservice.entity.vo.PublishCourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    PublishCourseVo getPublishCourseInfo(String id);

    void deleteByCourseId(String id);

    Map<String, Object> getCoursesPageList(CourseFrontVo courseFrontVo,Page<EduCourse> coursePage);

    CourseWebVo getFrontCourseInfo(String courseId);
}
