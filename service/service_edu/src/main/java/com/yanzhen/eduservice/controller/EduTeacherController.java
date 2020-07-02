package com.yanzhen.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.commonutils.R;
import com.yanzhen.eduservice.entity.EduTeacher;
import com.yanzhen.eduservice.entity.vo.TeacherQuery;
import com.yanzhen.eduservice.service.EduTeacherService;
import com.yanzhen.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-05-24
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("查找所有讲师")
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.ok();
        } else return R.error();
    }

    @GetMapping("/pageteacher/{current}/{limit}")
    public R pageTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    //PostMapping 和 RequestBod要一起使用
    @PostMapping("/pageteachercondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();
        String name = teacherQuery.getName();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(page, wrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("addteacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else return R.error();
    }

    @GetMapping("/getteacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @PostMapping("/update")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }
        return R.error();
    }
}

