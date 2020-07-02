package com.yanzhen.eduservice.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.entity.EduCourse;
import com.yanzhen.eduservice.entity.vo.CourseInfoVo;
import com.yanzhen.eduservice.entity.vo.PublishCourseVo;
import com.yanzhen.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addcourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("/getcourseinfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    @PostMapping("/updatecourseinfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    @GetMapping("/getpublishcourse/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        PublishCourseVo publishCourseVo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("publishinfo",publishCourseVo);
    }
    @PostMapping("/publishcourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }
    @GetMapping("/getcourses")
    public R getCourses(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }
    @DeleteMapping("/delete/{id}")
    public R deleteCourse(@PathVariable String id){
        eduCourseService.deleteByCourseId(id);
        return R.ok();
    }
}

