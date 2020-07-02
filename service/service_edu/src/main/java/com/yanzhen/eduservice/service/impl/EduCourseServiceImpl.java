package com.yanzhen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.eduservice.entity.EduChapter;
import com.yanzhen.eduservice.entity.EduCourse;
import com.yanzhen.eduservice.entity.EduCourseDescription;
import com.yanzhen.eduservice.entity.EduVideo;
import com.yanzhen.eduservice.entity.frontvo.CourseFrontVo;
import com.yanzhen.eduservice.entity.frontvo.CourseWebVo;
import com.yanzhen.eduservice.entity.vo.CourseInfoVo;
import com.yanzhen.eduservice.entity.vo.PublishCourseVo;
import com.yanzhen.eduservice.mapper.EduCourseMapper;
import com.yanzhen.eduservice.service.EduChapterService;
import com.yanzhen.eduservice.service.EduCourseDescriptionService;
import com.yanzhen.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanzhen.eduservice.service.EduVideoService;
import com.yanzhen.exceptionhandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


    @Autowired
    private EduCourseService eduCourseService;
    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new GuliException(20001,"添加课程信息失败");
        }
        String cid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i == 0){
            throw  new GuliException(20001,"修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public PublishCourseVo getPublishCourseInfo(String id) {
        PublishCourseVo publishCourseVo = baseMapper.getPublishCourseVo(id);
        return publishCourseVo;
    }

    @Override
    public void deleteByCourseId(String id) {
        eduVideoService.deleteByCourseId(id);
        eduChapterService.deleteByCourseId(id);
        eduCourseDescriptionService.removeById(id);
        eduCourseService.removeById(id);
    }

    @Override
    public Map<String, Object> getCoursesPageList(CourseFrontVo courseFrontVo,Page<EduCourse> coursePage) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage,wrapper);
        long current = coursePage.getCurrent();
        List<EduCourse> records = coursePage.getRecords();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        long pages = coursePage.getPages();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getFrontCourseInfo(String courseId) {
        return baseMapper.getFrontCourseInfo(courseId);
    }
}
