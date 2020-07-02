package com.yanzhen.eduorder.service;

import com.yanzhen.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-18
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderId);

    Map<String,String> queryPayState(String orderNo);

    void updateOrderStatus(Map<String,String> map);
}
