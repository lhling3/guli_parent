package com.ling.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.edu.client.VodClient;
import com.ling.edu.pojo.Video;
import com.ling.edu.mapper.VideoMapper;
import com.ling.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    private VodClient vodClient;

    @Autowired
    public VideoServiceImpl(VodClient vodClient) {
        this.vodClient = vodClient;
    }

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.select("video_source_id");
        List<Video> videos = baseMapper.selectList(queryWrapper);
        List<String> videoIdList = new ArrayList<>();
        for (Video video : videos) {
            String videoSourceId = video.getVideoSourceId();
            if(StringUtils.isEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        if(videoIdList.size()>0){
            vodClient.deleteBatchVideo(videoIdList);
        }
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

}
