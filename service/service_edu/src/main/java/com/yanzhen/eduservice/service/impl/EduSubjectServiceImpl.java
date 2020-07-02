package com.yanzhen.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.eduservice.entity.EduSubject;
import com.yanzhen.eduservice.entity.excel.SubjectData;
import com.yanzhen.eduservice.entity.subject.OneSubject;
import com.yanzhen.eduservice.entity.subject.TwoSubject;
import com.yanzhen.eduservice.listener.SubjectListener;
import com.yanzhen.eduservice.mapper.EduSubjectMapper;
import com.yanzhen.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject( MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream ins = file.getInputStream();
            EasyExcel.read(ins, SubjectData.class,new SubjectListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper);
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapper2);
        List<OneSubject> res = new ArrayList<>();
        for (int i = 0; i < oneSubjects.size(); i++) {
            OneSubject oneSubject = new OneSubject();
            EduSubject one = oneSubjects.get(i);
            BeanUtils.copyProperties(one,oneSubject);
            res.add(oneSubject);
            List<TwoSubject> res2 = new ArrayList<>();
            for (int i1 = 0; i1 < twoSubjects.size(); i1++) {
                EduSubject twoSubject = twoSubjects.get(i1);
                if(twoSubject.getParentId().equals(one.getId())){
                    TwoSubject twoSubject1 = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject,twoSubject1);
                    res2.add(twoSubject1);
                }
            }
            oneSubject.setChildren(res2);
        }
        return res;
    }
}
