package com.ling.vodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author Ling
 * @date 2022/4/19 17:23
 */
public class TestVod {
    public static void main(String[] args) {
        getPlayUrl();
        try {
            getPlayAuth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void getPlayAuth() throws Exception {
        //根据视频id获取视频播放凭证
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t7BoHzybdXQ92QLwZWE", "CkE9lqmtIl7EwgkTgD1iWA9kmUVXkg");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("40a1cea7b85e483eb61eab0d66997422");
        response = client.getAcsResponse(request);
        System.out.println("PlayAuth"+response.getPlayAuth());
    }
    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("40a1cea7b85e483eb61eab0d66997422");
        return client.getAcsResponse(request);
    }
    public static void getPlayUrl(){
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t7BoHzybdXQ92QLwZWE", "CkE9lqmtIl7EwgkTgD1iWA9kmUVXkg");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
