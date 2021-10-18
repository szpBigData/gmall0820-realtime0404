package com.atguigu.gmall.service2.impl;

import com.atguigu.gmall.bean2.ProvinceStats;
import com.atguigu.gmall.mapper2.ProvinceStatsMappe;
import com.atguigu.gmall.service2.ProvinceStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-13 7:03
 */
@Service
public class ProvinceStatsServiceImpl implements ProvinceStatsService {
        @Autowired
    ProvinceStatsMappe provinceStatsMapper;
    @Override
    public List<ProvinceStats> selectProvinceStats(int date) {
        return provinceStatsMapper.selectProvinceStats(date);
    }
}
