package com.ling.order.controller;


import com.ling.commonutils.R;
import com.ling.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Ling
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/eduorder/paylog")

public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回二维码地址，还有其他信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
       Map<String,String> map = payLogService.queryPayStatus(orderNo);
       if(map == null){
           return R.error().message("支付出错");
       }
       if(map.get("trade_state").equals("SUCCESS")){
           //添加记录到支付表，更新订单表的支付状态
           payLogService.updateOrderStatus(map);
           return R.ok().message("支付成功");
       }
       return R.ok().code(25000).message("支付中");
    }
}

