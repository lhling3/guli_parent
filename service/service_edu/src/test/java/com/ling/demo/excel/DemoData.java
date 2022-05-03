package com.ling.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Ling
 * @date 2022/4/14 20:06
 */
@Data
public class DemoData {
    //设置excel表头名称,index = 0表示第一列
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
