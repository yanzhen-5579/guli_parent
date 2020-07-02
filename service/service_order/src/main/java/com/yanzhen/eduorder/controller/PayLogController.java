package com.yanzhen.eduorder.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.eduorder.service.PayLogService;
import com.yanzhen.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("/createnative/{orderId}")
    public R createNativeByOrderId(@PathVariable String orderId){
        Map<String,Object> map = payLogService.createNative(orderId);
        System.out.println("生成二维码"+map);
        return R.ok().data("map",map);
    }

    @GetMapping("/queryorderstate/{orderNo}")
    public R queryOrderState(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayState(orderNo);
        System.out.println("查询订单状态"+map);
        if(map == null){
            throw new GuliException(20001,"支付出错");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

