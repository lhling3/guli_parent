package com.ling.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ling
 * @date 2022/4/12 19:24
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
