package com.atguigu.gmall.service3;

import com.atguigu.gmall.bean3.ProvinceStats;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-24 19:43
 */

public interface ProvinceStatsService {
    public List<ProvinceStats> selectProvinceStats(int date);
}
