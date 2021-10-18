package com.atguigu.gmall.service2;

import com.atguigu.gmall.bean2.ProvinceStats;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-13 7:02
 */
public interface ProvinceStatsService {
    public List<ProvinceStats> selectProvinceStats(int date);
}
