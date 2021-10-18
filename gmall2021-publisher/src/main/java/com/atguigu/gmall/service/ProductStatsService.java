package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.ProductStats;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-05-09 21:17
 */
public interface ProductStatsService {
        public BigDecimal getGMV(int date);

        public List<ProductStats> getProductStatsGroupByCategory3(int date,int limit);

        public List<ProductStats> getProductStatsByTrademark(int date,int limit);

        public List<ProductStats> getProductStatsGroupBySpu(int date,int limit);
}
