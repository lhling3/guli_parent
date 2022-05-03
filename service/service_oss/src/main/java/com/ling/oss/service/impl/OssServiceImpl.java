package com.ling.oss.service.impl;

import com.ling.oss.service.OssService;
import com.ling.oss.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author Ling
 * @date 2022/4/12 19:23
 */
@Service
public class OssServiceImpl implements OssService {
    private final QiniuUtils qiniuUtils;

    @Autowired
    public OssServiceImpl(QiniuUtils qiniuUtils) {
        this.qiniuUtils = qiniuUtils;
    }

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //原始文件名称
        String originalFilename = file.getOriginalFilename();
        //唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //把文件按照日期进行分类
        String datePath = new DateTime().toString("yyyy/MM/dd");
        fileName = datePath + "/" + fileName;
        //上传文件，上传到哪？七牛云
        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return  QiniuUtils.url + fileName;
        }else{
            return null;
        }
    }
}
