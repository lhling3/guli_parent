package com.ling.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.cms.pojo.CrmBanner;
import com.ling.cms.service.CrmBannerService;
import com.ling.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-24
 */
@RestController
@RequestMapping("/educms/banneradmin")

public class BannerAdminController {
    private final CrmBannerService crmBannerService;

    @Autowired
    public BannerAdminController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    /**
     * 分页查询Banner
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        crmBannerService.page(bannerPage,null);

        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    /**
     * 添加Banner
     * @param crmBanner
     * @return
     */
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 删除Banner
     * @param id
     * @return
     */
    @DeleteMapping("removeBanner/{id}")
    public R removeBanner(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }

    /**
     * 查询Banner
     * @param id
     * @return
     */
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("banner",crmBanner);
    }

    /**
     * 修改Banner
     * @param crmBanner
     * @return
     */
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }
}

