package com.yanzhen.eduorder.service.impl;

import com.yanzhen.commonutils.CourseWebVoOrder;
import com.yanzhen.commonutils.UcenterMemberOrder;
import com.yanzhen.eduorder.client.CourseClient;
import com.yanzhen.eduorder.client.UserClient;
import com.yanzhen.eduorder.entity.Order;
import com.yanzhen.eduorder.mapper.OrderMapper;
import com.yanzhen.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanzhen.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private UserClient userClient;

    @Autowired
    private CourseClient courseClient;

    @Override
    public String getOrderInfo(String id, String memberId) {
        UcenterMemberOrder userOrder = userClient.getUserOrder(memberId);
        CourseWebVoOrder courseOrder = courseClient.getCourseOrder(id);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(id);
        order.setCourseTitle(courseOrder.getTitle());
        order.setCourseCover(courseOrder.getCover());
        order.setTeacherName(courseOrder.getTeacherName());
        order.setTotalFee(courseOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userOrder.getMobile());
        order.setNickname(userOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
