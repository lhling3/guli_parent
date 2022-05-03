package com.ling.edu.pojo.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ling
 * @date 2022/4/16 21:28
 */
@Data
public class ChapterVo {
    private String id;
    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
