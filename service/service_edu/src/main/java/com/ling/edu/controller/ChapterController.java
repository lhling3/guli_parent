package com.ling.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.commonutils.R;
import com.ling.edu.pojo.Chapter;
import com.ling.edu.pojo.chapter.ChapterVo;
import com.ling.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class ChapterController {
    private final ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    /**
     * 得到课程大纲列表，根据课程id进行查询
     * @return R
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     * 添加章节
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    /**
     * 查询章节信息
     * @param id
     * @return
     */
    @GetMapping("getChapterInfo/{id}")
    public R getChapterInfo(@PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }
    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id){
        boolean isDelete = chapterService.deleteChapter(id);
        if(isDelete){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

