package com.ling.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.edu.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-04
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> listHotTeacher();

    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);
}
