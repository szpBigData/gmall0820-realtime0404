package com.atguigu.gmall.service2;

import com.atguigu.gmall.bean2.ProductStats;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-10 19:45
 */
public interface ProductStatsService {
    public BigDecimal getGMV(int date);
    public List<ProductStats> getProductStatsGroupBySpu(@Param("date") int date, @Param("limit") int limit);

    public List<ProductStats> getProductStatsGroupByCategory(@Param("date") int date,@Param("limit") int limit);

    public List<ProductStats> getProductStatsByTrademark(@Param("date") int date,@Param("limit") int limit);
}
