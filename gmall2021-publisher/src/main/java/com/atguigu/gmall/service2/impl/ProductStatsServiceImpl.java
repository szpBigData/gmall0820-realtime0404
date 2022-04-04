package com.atguigu.gmall.service2.impl;

import com.atguigu.gmall.bean2.ProductStats;
import com.atguigu.gmall.mapper2.ProductStatsMapper;
import com.atguigu.gmall.service2.ProductStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-10 20:05
 */
@Service
public class ProductStatsServiceImpl implements ProductStatsService {
    @Autowired
    ProductStatsMapper productStatsMapper;

    @Override
    public BigDecimal getGMV(int date) {
        return productStatsMapper.getGMV(date);
    }

    @Override
    public List<ProductStats> getProductStatsGroupBySpu(int date, int limit) {
        return productStatsMapper.getProductStatsGroupBySpu(date,limit);
    }

    @Override
    public List<ProductStats> getProductStatsGroupByCategory(int date, int limit) {
        return productStatsMapper.getProductStatsGroupBySpu(date,limit);
    }

    @Override
    public List<ProductStats> getProductStatsByTrademark(int date, int limit) {
        return productStatsMapper.getProductStatsGroupBySpu(date,limit);
    }
}
