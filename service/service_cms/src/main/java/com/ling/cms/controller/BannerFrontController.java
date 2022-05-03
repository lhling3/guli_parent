package com.ling.cms.controller;

import com.ling.cms.pojo.CrmBanner;
import com.ling.cms.service.CrmBannerService;
import com.ling.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ling
 * @date 2022/4/24 14:41
 */
@RestController
@RequestMapping("/educms/bannerfront")

public class BannerFrontController {
    private final CrmBannerService crmBannerService;

    @Autowired
    public BannerFrontController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    /**
     * 查询所有Banner
     * @return
     */
    @GetMapping("getAllBanner")
    public R getAllBanner(){

        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
