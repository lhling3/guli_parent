package com.ling.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ling.commonutils.R;
import com.ling.servicebase.exceptionhandler.GuliException;
import com.ling.vod.service.VodService;
import com.ling.vod.utils.ConstantPropertiesUtil;
import com.ling.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ling
 * @date 2022/4/20 15:59
 */
@RestController
@RequestMapping("/eduvod/video")

public class VodController {
    private final VodService vodService;

    @Autowired
    public VodController(VodService vodService) {
        this.vodService = vodService;
    }

    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideoToAliyun(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 删除一个阿里云视频的方法
     * @param videoId
     * @return
     */
    @DeleteMapping("removeVideo/{videoId}")
    public R removeVideo(@PathVariable String videoId){
        return vodService.removeVideo(videoId);
    }

    /**
     * 删除多个阿里云视频的方法
     * @param videoIdList
     * @return
     */
    @DeleteMapping("deleteBatch")
    public R deleteBatchVideo(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMuliVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频Id获取视频播放凭证
     */
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e){
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
