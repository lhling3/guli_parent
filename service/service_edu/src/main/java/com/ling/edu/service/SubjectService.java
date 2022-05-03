package com.ling.edu.service;

import com.ling.edu.pojo.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.edu.pojo.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-14
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file,SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
