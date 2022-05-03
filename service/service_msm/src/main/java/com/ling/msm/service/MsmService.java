package com.ling.msm.service;

import java.util.Map;

/**
 * @author Ling
 * @date 2022/4/24 20:36
 */
public interface MsmService {

    boolean send(String phone, String templateCode, Map<String, Object> param);
}
