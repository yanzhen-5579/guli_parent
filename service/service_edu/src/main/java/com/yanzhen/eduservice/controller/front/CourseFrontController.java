package com.yanzhen.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.commonutils.CourseWebVoOrder;
import com.yanzhen.commonutils.JwtUtils;
import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.client.OrderClient;
import com.yanzhen.eduservice.entity.EduCourse;
import com.yanzhen.eduservice.entity.chapter.ChapterVo;
import com.yanzhen.eduservice.entity.frontvo.CourseFrontVo;
import com.yanzhen.eduservice.entity.frontvo.CourseWebVo;
import com.yanzhen.eduservice.service.EduChapterService;
import com.yanzhen.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getcoursesinfo/{page}/{limit}")
    public R getCoursesInfo(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String,Object> map = eduCourseService.getCoursesPageList(courseFrontVo,coursePage);
        return R.ok().data(map);
    }

    @GetMapping("/getcoursefrontinfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId, HttpServletRequest request){
        CourseWebVo courseWebVo = eduCourseService.getFrontCourseInfo(courseId);
        List<ChapterVo> chapter = eduChapterService.getChapterVideoByCourseId(courseId);
        if(request.getHeader("token") == null){
            return R.ok().data("login",false);
        }
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean isbuy = orderClient.isBuyCourse(courseId, memberIdByJwtToken);
        return R.ok().data("courseWebVo",courseWebVo).data("chapter",chapter).data("isbuy",isbuy);
    }

    @PostMapping("/getcourseorder/{id}")
    public CourseWebVoOrder getCourseOrder(@PathVariable String id){
        CourseWebVo frontCourseInfo = eduCourseService.getFrontCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(frontCourseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
