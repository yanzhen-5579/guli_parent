package com.yanzhen.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.eduservice.entity.EduSubject;
import com.yanzhen.eduservice.entity.excel.SubjectData;
import com.yanzhen.eduservice.service.EduSubjectService;
import com.yanzhen.exceptionhandler.GuliException;

public class SubjectListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService eduSubjectService;
    public SubjectListener(){};
    public SubjectListener(EduSubjectService eduSubjectService){
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件内容为空");
        }
        EduSubject oneSubject = this.oneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(oneSubject == null){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        String pid = oneSubject.getId();
        EduSubject twoSubject = this.twoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if(twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            eduSubjectService.save(twoSubject);
        }
    }

    private EduSubject oneSubject(EduSubjectService eduSubjectService,String name){
        Wrapper<EduSubject> wrapper = new QueryWrapper<>();
        ((QueryWrapper<EduSubject>) wrapper).eq("title",name);
        ((QueryWrapper<EduSubject>) wrapper).eq("parent_id",0);
        EduSubject res = eduSubjectService.getOne(wrapper);
        return res;
    }
    private EduSubject twoSubject(EduSubjectService eduSubjectService,String name,String pid){
        Wrapper<EduSubject> wrapper = new QueryWrapper<>();
        ((QueryWrapper<EduSubject>) wrapper).eq("title",name);
        ((QueryWrapper<EduSubject>) wrapper).eq("parent_id",pid);
        EduSubject res = eduSubjectService.getOne(wrapper);
        return res;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
