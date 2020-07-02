package com.yanzhen.eduservice.service;

import com.yanzhen.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanzhen.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
