package com.yanzhen.educms.service;

import com.yanzhen.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-10
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAll();
}
