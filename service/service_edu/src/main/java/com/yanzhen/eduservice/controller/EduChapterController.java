package com.yanzhen.eduservice.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.entity.EduChapter;
import com.yanzhen.eduservice.entity.chapter.ChapterVo;
import com.yanzhen.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getchaptervideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }
    @PostMapping("/addchapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }
    @GetMapping("/getchapter/{id}")
    public R getChapterbyId(@PathVariable String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.ok().data("eduChapter",eduChapter);
    }
    @PostMapping("/updatechapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }
    @DeleteMapping("{id}")
    public R deleteChapter(@PathVariable String id){
        boolean flag = eduChapterService.deleteById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

