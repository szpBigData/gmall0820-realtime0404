package com.atguigu.gmall.service3.impl;

import com.atguigu.gmall.bean3.ProvinceStats;
import com.atguigu.gmall.mapper3.ProvinceStatsMapper;
import com.atguigu.gmall.service3.ProvinceStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-24 19:43
 */
@Service
public class ProvinceStatsServiceImpl implements ProvinceStatsService {
    @Autowired
    ProvinceStatsMapper provinceStatsMapper;
    @Override
    public List<ProvinceStats> selectProvinceStats(int date) {
        return provinceStatsMapper.selectProvinceStats(date);
    }
}
