package com.ling.edu.pojo.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ling
 * @date 2022/4/15 15:24
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    //一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
