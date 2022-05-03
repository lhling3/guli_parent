package com.ling.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ling
 * @date 2022/4/14 20:08
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        /*//实现excel写操作
        //1、设置写入文件夹地址和文件名称
        String filename = "E:\\编程学习资料\\练手项目资料\\谷粒学院\\excel\\write.xlsx";

        //2、调用easyexcel里面的方法实现写操作
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());*/

        //读操作
        String filename = "E:\\编程学习资料\\练手项目资料\\谷粒学院\\excel\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+ i);
            list.add(data);
        }
        return list;
    }
}
