package com.yanzhen.eduorder.service;

import com.yanzhen.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-18
 */
public interface OrderService extends IService<Order> {

    String getOrderInfo(String id, String memberIdByJwtToken);
}
