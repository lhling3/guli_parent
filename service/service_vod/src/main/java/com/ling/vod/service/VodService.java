package com.ling.vod.service;

import com.ling.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ling
 * @date 2022/4/20 16:05
 */
public interface VodService {
    String uploadVideoToAliyun(MultipartFile file);

    R removeVideo(String videoId);

    void removeMuliVideo(List<String> videoIdList);
}
