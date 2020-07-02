package com.yanzhen.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.entity.EduCourse;
import com.yanzhen.eduservice.entity.EduTeacher;
import com.yanzhen.eduservice.service.EduCourseService;
import com.yanzhen.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/front")
public class IndexFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("/index")
    public R index(){
        QueryWrapper<EduCourse> eduWrapper = new QueryWrapper<>();
        eduWrapper.orderByDesc("id");
        eduWrapper.last("limit 8");
        List<EduCourse> eduList = eduCourseService.list(eduWrapper);

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherWrapper);

        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
