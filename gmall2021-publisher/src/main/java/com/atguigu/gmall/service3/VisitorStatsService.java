package com.atguigu.gmall.service3;

import com.atguigu.gmall.bean3.VisitorStats;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-24 22:13
 */
public interface VisitorStatsService {
    public List<VisitorStats> gselectVisitorStatsByNewFlag(int date);
    public Long  selectUv(int date);
    public Long  selectPv(int date);
    public List<VisitorStats> selectVisitorStatsByHour(int date);
}
