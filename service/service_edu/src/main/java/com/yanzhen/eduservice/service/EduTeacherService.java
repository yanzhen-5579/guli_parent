package com.yanzhen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-05-24
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeachersPage(Page<EduTeacher> pageTeacher);
}
