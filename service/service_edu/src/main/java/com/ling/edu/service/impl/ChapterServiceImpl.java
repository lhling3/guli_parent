package com.ling.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.edu.pojo.Chapter;
import com.ling.edu.mapper.ChapterMapper;
import com.ling.edu.pojo.Video;
import com.ling.edu.pojo.chapter.ChapterVo;
import com.ling.edu.pojo.chapter.VideoVo;
import com.ling.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ling.edu.service.VideoService;
import com.ling.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    private final VideoService videoService;

    @Autowired
    public ChapterServiceImpl(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1、根据课程id查询所有章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<Chapter> chapterList = this.list(wrapperChapter);
        //2、根据课程id查询所有小节
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> videoList = videoService.list(wrapperVideo);
        //3、遍历查询出来的章节列表，进行封装
        List<ChapterVo> finalList = new ArrayList<>();
        for(Chapter chapter : chapterList){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalList.add(chapterVo);
            //4、遍历查询出来的小节列表，进行封装
            List<VideoVo> childrenList = new ArrayList<>();
            for (Video video : videoList) {
                if(video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    childrenList.add(videoVo);
                }
            }
            chapterVo.setChildren(childrenList);
        }
        return finalList;
    }

    /**
     * 删除章节
     * @param id
     */
    @Override
    public boolean deleteChapter(String id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        int count = videoService.count(wrapper);
        if(count == 0){
            int result = baseMapper.deleteById(id);
            return result>0;
        }else{
            throw new GuliException(20001,"有小节，不能删除");
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
