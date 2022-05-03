package com.ling.oss.controller;

import com.ling.commonutils.R;
import com.ling.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ling
 * @date 2022/4/12 19:23
 */
@RestController
@RequestMapping("/eduoss/qiniuyunfileoss")

public class OssController {
    private final OssService ossService;

    @Autowired
    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    /**
     * 上传头像的方法
     */
    @PostMapping("upload")
    public R uploadOssFile(MultipartFile file){
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
