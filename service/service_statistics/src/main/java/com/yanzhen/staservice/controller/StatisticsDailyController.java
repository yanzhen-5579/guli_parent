package com.yanzhen.staservice.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;


    //调用UCenter中的方法查询注册人数 并写入
    @PostMapping("/countregister/{day}")
    public R countRegister(@PathVariable String day){
        statisticsDailyService.countRegister(day);
        return R.ok();
    }

    //获取统计数据
    @GetMapping("/getData/{type}/{begin}/{end}")
    public R getData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map = statisticsDailyService.getData(type,begin,end);
        return R.ok().data(map);
    }
}

