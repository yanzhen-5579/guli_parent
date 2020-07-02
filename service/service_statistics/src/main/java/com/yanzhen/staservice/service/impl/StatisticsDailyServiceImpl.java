package com.yanzhen.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanzhen.commonutils.R;
import com.yanzhen.staservice.client.UcenterClient;
import com.yanzhen.staservice.entity.StatisticsDaily;
import com.yanzhen.staservice.mapper.StatisticsDailyMapper;
import com.yanzhen.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-29
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {


    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void countRegister(String day) {
        //在添加之前，先把原来的数据删除
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.eq("date_calculated",day);
        baseMapper.delete(statisticsDailyQueryWrapper);
        //查询注册数据
        R registerR = ucenterClient.countRegister(day);
        Integer countregister = (Integer) registerR.getData().get("countregister");
        //查询出数据以后 往表中添加
        StatisticsDaily statisticsDaily  = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(countregister);
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getData(String type, String begin, String end) {
        //先从数据库中查询数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        //对数据进行封装
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            dateList.add(daily.getDateCalculated());
            //查询对应数据进行封装
            switch (type){
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //进行数据的返回
        Map<String,Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);
        return map;
    }
}
