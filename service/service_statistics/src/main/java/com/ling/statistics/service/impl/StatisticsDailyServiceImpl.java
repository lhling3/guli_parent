package com.ling.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.commonutils.R;
import com.ling.statistics.client.UcenterClient;
import com.ling.statistics.pojo.StatisticsDaily;
import com.ling.statistics.mapper.StatisticsDailyMapper;
import com.ling.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Ling
 * @since 2022-05-02
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    private final UcenterClient ucenterClient;

    @Autowired
    public StatisticsDailyServiceImpl(UcenterClient ucenterClient) {
        this.ucenterClient = ucenterClient;
    }

    @Override
    public void registerCount(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        R r = ucenterClient.countRegister(day);
        Integer count = (Integer) r.getData().get("count");
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(count);
        sta.setDateCalculated(day);
        sta.setVideoViewNum(RandomUtils.nextInt(100,999));
        sta.setLoginNum(RandomUtils.nextInt(100,999));
        sta.setCourseNum(RandomUtils.nextInt(10,99));
        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //封装数据
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        for (StatisticsDaily statisticsDaily : staList) {
           dateList.add( statisticsDaily.getDateCalculated());
           switch (type){
               case "login_num":
                   dataList.add(statisticsDaily.getLoginNum());
                   break;
               case "register_num":
                   dataList.add(statisticsDaily.getRegisterNum());
                   break;
               case "video_view_num":
                   dataList.add(statisticsDaily.getVideoViewNum());
                   break;
               case "course_num":
                   dataList.add(statisticsDaily.getCourseNum());
                   break;
               default:
                   break;
           }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dataList",dataList);
        map.put("dateList",dateList);
        return map;
    }
}
