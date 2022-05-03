package com.ling.edu.service;

import com.ling.edu.pojo.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-15
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);

}
