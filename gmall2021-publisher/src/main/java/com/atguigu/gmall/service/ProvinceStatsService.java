package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.ProvinceStats;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-05-09 22:31
 */
public interface ProvinceStatsService {
    public List<ProvinceStats> selectProvinceStats(int date);
}
