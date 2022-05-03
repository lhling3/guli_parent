package com.ling.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.edu.listener.SubjectExcelListener;
import com.ling.edu.pojo.Subject;
import com.ling.edu.mapper.SubjectMapper;
import com.ling.edu.pojo.excel.SubjectData;
import com.ling.edu.pojo.subject.OneSubject;
import com.ling.edu.pojo.subject.TwoSubject;
import com.ling.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
 * @since 2022-04-14
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    /*private SubjectMapper subjectMapper;

    @Autowired
    public SubjectServiceImpl(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }*/

    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try{
            //文件输入流
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询课程列表
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1、查询所有一级分类
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<Subject> oneSubjectList = this.list(wrapperOne);
        //2、查询所有二级分类
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<Subject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建List集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //3、封装一级分类
        for(Subject sub : oneSubjectList){
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(sub,oneSubject);
            finalSubjectList.add(oneSubject);
            //在一级分类循环遍历查询所有的二级分类
            //创建List集合封装每一个分类的二级分类
            List<TwoSubject> twoList = new ArrayList<>();
            for(Subject twoSub : twoSubjectList){
                //4、封装二级分类
                if(twoSub.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSub,twoSubject);
                    twoList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoList);
        }

        return finalSubjectList;
    }
}
