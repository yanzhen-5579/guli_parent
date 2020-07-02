package com.yanzhen.educms.controller;


import com.yanzhen.commonutils.R;
import com.yanzhen.educms.entity.CrmBanner;
import com.yanzhen.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/educms/crm-banner/front")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/getall")
    public R getAllBanners() {
        List<CrmBanner> list = crmBannerService.selectAll();
        return R.ok().data("list",list);
    }

}

