package com.ling.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.edu.pojo.Subject;
import com.ling.edu.pojo.excel.SubjectData;
import com.ling.edu.service.SubjectService;
import com.ling.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Ling
 * @date 2022/4/14 21:08
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    private SubjectService subjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 读取excel中的内容
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        Subject oneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        //没有相同一级分类，进行添加
        if(oneSubject == null){
            oneSubject = new Subject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }
        //对应的一级分类id值
        String pid = oneSubject.getId();
        Subject twoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(twoSubject == null){
            twoSubject = new Subject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }
    }

    /**
     * 判断一级分类不能重复添加
     */
    private Subject existOneSubject(SubjectService subjectService,String name){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
    /**
     * 判断二级分类不能重复添加
     */
    private Subject existTwoSubject(SubjectService subjectService,String name,String pid){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
