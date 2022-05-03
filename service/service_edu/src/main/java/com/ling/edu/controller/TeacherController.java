package com.ling.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.R;
import com.ling.edu.pojo.Teacher;
import com.ling.edu.pojo.vo.TeacherQuery;
import com.ling.edu.service.TeacherService;
import com.ling.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-04
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")

public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    /**
     * 查询讲师表中的所有数据
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service方法实现查询操作
        List<Teacher> listTeacher = teacherService.list(null);
        return R.ok().data("items",listTeacher);
    }
    /**
     * 逻辑删除讲师
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable("id") String id){
        boolean flag = teacherService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询讲师
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") int current,@PathVariable("limit") int limit){
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current,limit);
        teacherService.page(pageTeacher,null);

        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<Teacher> records = pageTeacher.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    /**
     * 条件查询分页方法
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") int  current, @PathVariable("limit") int limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> pageTeacher = new Page<>(current,limit);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        //多条件组合查询，判断条件是否为空，不为空则拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            //构建条件，模糊查询
            wrapper.like("name",name);
        }
        if(level != null){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher,wrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<Teacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);

    }

    /**
     * 添加讲师接口的方法
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if(save) {
            return R.ok();
        }else {
            return R.error();
        }
    }
    /**
     * 根据讲师id查询讲师
     */
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        /*try{
            int i=10/0;
        }catch (Exception e){
            throw new GuliException(20001,"执行了自定义异常处理");
        }*/
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 修改讲师功能
     */
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

