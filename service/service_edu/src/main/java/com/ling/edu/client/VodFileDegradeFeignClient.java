package com.ling.edu.client;

import com.ling.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ling
 * @date 2022/4/22 21:06
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatchVideo(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
