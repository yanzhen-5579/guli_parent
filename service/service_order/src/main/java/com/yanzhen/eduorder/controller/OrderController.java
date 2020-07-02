package com.yanzhen.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.commonutils.JwtUtils;
import com.yanzhen.commonutils.R;
import com.yanzhen.eduorder.entity.Order;
import com.yanzhen.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/getorderinfo/{id}")
    public R getOrderInfo(@PathVariable String id, HttpServletRequest request){
        String orderNo = orderService.getOrderInfo(id, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderNo",orderNo);
    }

    @GetMapping("/getorderbyno/{ono}")
    public R getOrderByOno(@PathVariable String ono){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",ono);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
    @GetMapping("/isbuycourse/{courseId}/{memberId}")
    public boolean isBuyCourse( @PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count > 0){
            return  true;
        }else {
            return false;
        }
    }
}

