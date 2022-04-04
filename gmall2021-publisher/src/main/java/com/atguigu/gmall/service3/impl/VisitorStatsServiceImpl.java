package com.atguigu.gmall.service3.impl;

import com.atguigu.gmall.bean3.VisitorStats;
import com.atguigu.gmall.mapper3.VisitorStatsMapper;
import com.atguigu.gmall.service3.VisitorStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-24 22:13
 */
@Service
public class VisitorStatsServiceImpl implements VisitorStatsService {
    @Autowired
    VisitorStatsMapper visitorStatsMapper;

    @Override
    public List<VisitorStats> gselectVisitorStatsByNewFlag(int date) {
        return visitorStatsMapper.gselectVisitorStatsByNewFlag(date);
    }

    @Override
    public Long selectUv(int date) {
        return visitorStatsMapper.selectUv(date);
    }

    @Override
    public Long selectPv(int date) {
        return visitorStatsMapper.selectPv(date);
    }

    @Override
    public List<VisitorStats> selectVisitorStatsByHour(int date) {
        return visitorStatsMapper.selectVisitorStatsByHour(date);
    }
}
