package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.VisitorStats;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-05-09 22:46
 */
public interface VisitorStatsService {
    public List<VisitorStats> selectVisitorStatsByNewFlag(int date);
    public List<VisitorStats> selectVisitorStatsByHour(int date);
    public Long selectPv(int date);
    public Long selectUv(int date);
}
