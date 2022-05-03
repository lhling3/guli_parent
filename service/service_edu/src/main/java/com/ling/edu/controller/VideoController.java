package com.ling.edu.controller;


import com.ling.commonutils.R;
import com.ling.edu.client.VodClient;
import com.ling.edu.pojo.Video;
import com.ling.edu.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/eduservice/video")

public class VideoController {
    private final VideoService videoService;
    private final VodClient vodClient;

    @Autowired
    public VideoController(VideoService videoService, VodClient vodClient) {
        this.videoService = videoService;
        this.vodClient = vodClient;
    }
    /**
     * 添加小节
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }

    /**
     * 删除小节,同时删除阿里云中的视频
     */
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isBlank(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 查询小节信息
     */
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id){
        Video video = videoService.getById(id);
        return R.ok().data("video",video);
    }
    /**
     * 修改小节
     */
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return R.ok();
    }
}

