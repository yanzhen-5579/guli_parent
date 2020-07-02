package com.yanzhen.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanzhen.commonutils.R;
import com.yanzhen.educms.entity.CrmBanner;
import com.yanzhen.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/educms/crm-banner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/pagebanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<CrmBanner>(page,limit);
        crmBannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }
    @PostMapping("/addbanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }
    @GetMapping("/getbanner/{id}")
    public R getById(@PathVariable String id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item",banner);
    }
    @PostMapping("/update")
    public R update(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }

}

