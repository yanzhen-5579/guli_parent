package com.yanzhen.staservice.service;

import com.yanzhen.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countRegister(String day);

    Map<String, Object> getData(String type, String begin, String end);
}
