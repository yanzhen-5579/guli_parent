package com.yanzhen.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.entity.EduCourse;
import com.yanzhen.eduservice.entity.EduTeacher;
import com.yanzhen.eduservice.service.EduCourseService;
import com.yanzhen.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/front")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/getteacherspage/{page}/{limit}")
    public R getTeachersPage(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getTeachersPage(pageTeacher);
        return R.ok().data(map);
    }

    @GetMapping("/getteacherinfo/{id}")
    public  R getTeacherInfo(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courseList = eduCourseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
