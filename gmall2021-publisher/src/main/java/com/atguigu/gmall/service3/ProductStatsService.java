package com.atguigu.gmall.service3;

import com.atguigu.gmall.bean3.ProductStats;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-23 23:07
 */
public interface ProductStatsService {
    public BigDecimal getGmv(int date);
    public List<ProductStats> getProductStatsGroupBySpu(@Param("date") int date, @Param("limit") int limit);
    public List<ProductStats>   getProductStatsGroupByCategory3(@Param("date")int date,@Param("limit")int limit);
    public List<ProductStats> getProductStatsByTrademark(@Param("date")int date,  @Param("limit") int limit);
}
