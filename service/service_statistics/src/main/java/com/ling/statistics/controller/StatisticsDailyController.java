package com.ling.statistics.controller;


import com.ling.commonutils.R;
import com.ling.statistics.client.UcenterClient;
import com.ling.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Ling
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/edusta/sta")

public class StatisticsDailyController {
    private final StatisticsDailyService statisticsDailyService;


    @Autowired
    public StatisticsDailyController(StatisticsDailyService statisticsDailyService) {
        this.statisticsDailyService = statisticsDailyService;
    }

    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示，返回日期和数量的json数组
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }
}

