package com.atguigu.gmall.mapper2;

import com.atguigu.gmall.bean2.ProductStats;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-10 16:27
 * 1.商品交易额不同维度统计
 *     品牌   水平柱状图
 *     品类   饼状图
 *     商品spu  轮播图
 * 这三个可以根据商品统计信息计算出来
 * 这三个图基本上都是根据用不同维度进行分组，金额进行聚合的方式查询商品统计表。直接先实现三个sql查询
 *
 */
public interface ProductStatsMapper {
    @Select("SELECT spu_id,\n" +
            "spu_name,\n" +
            "SUM(order_amount) order_amount,\n" +
            "SUM(order_ct) order_ct\n" +
            "FROM product_stats_2021\n" +
            "WHERE toYYYYMMDD(stt)=#{date}\n" +
            "GROUP BY\n" +
            "spu_id,spu_name\n" +
            "HAVING order_amount>0\n" +
            "ORDER BY order_amount\n" +
            "DESC\n" +
            "LIMIT  #{limit}")
    public List<ProductStats> getProductStatsGroupBySpu(@Param("date") int date, @Param("limit") int limit);
    @Select("SELECT tm_id,\n" +
            "tm_name,\n" +
            "SUM(order_amount) order_amount\n" +
            "FROM product_stats_2021\n" +
            "WHERE toYYYYMMDD(stt)=#{date}\n" +
            "GROUP BY tm_id,rm_name\n" +
            "HAVING order_amount>0\n" +
            "ORDER BY order_amount\n" +
            "DESC \n" +
            "LIMIT #{limit}")
    public List<ProductStats> getProductStatsGroupByCategory(@Param("date") int date,@Param("limit") int limit);

    @Select("SELECT \n" +
            "tm_id,tm_name,\n" +
            "SUM(order_amount) order_amount,\n" +
            "FROM \n" +
            "product_stats_2021\n" +
            "WHERE toYYYYMMDD(stt)=#{date}\n" +
            "GROUP BY tm_id,tm_name\n" +
            "HAVING order_amount>0\n" +
            "ORDER BY order_amount\n" +
            "DESC\n" +
            "limmit #{limit}")
    public List<ProductStats> getProductStatsByTrademark(@Param("date") int date,@Param("limit") int limit);
}
