package com.atguigu.gmall.service3.impl;

import com.atguigu.gmall.bean3.ProductStats;
import com.atguigu.gmall.mapper3.ProductStatsMapper;
import com.atguigu.gmall.service3.ProductStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-23 23:08
 */
@Service
public class ProductStatsServiceImpl  implements ProductStatsService {
    @Autowired
    ProductStatsMapper productStatsMapper;
    @Override
    public BigDecimal getGmv(int date) {
        return productStatsMapper.getGmv(date);
    }

    @Override
    public List<ProductStats> getProductStatsGroupBySpu(int date, int limit) {
        return productStatsMapper.getProductStatsGroupBySpu(date,limit);
    }

    @Override
    public List<ProductStats> getProductStatsGroupByCategory3(int date, int limit) {
        return productStatsMapper.getProductStatsGroupByCategory3(date,limit);
    }

    @Override
    public List<ProductStats> getProductStatsByTrademark(int date, int limit) {
        return productStatsMapper.getProductStatsByTrademark(date,limit);
    }
}
