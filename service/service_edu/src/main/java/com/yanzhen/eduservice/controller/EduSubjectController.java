package com.yanzhen.eduservice.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.entity.subject.OneSubject;
import com.yanzhen.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addsubject")
    public R addSubject(@RequestParam("file") MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }
    @GetMapping("/getallsubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }
}

