package com.ling.edu.service;

import com.ling.edu.pojo.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.edu.pojo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String id);

    void removeChapterByCourseId(String courseId);
}
