package com.atguigu.gmall.mapper3;
import com.atguigu.gmall.bean3.ProductStats;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-23 23:04
 */
public interface ProductStatsMapper {
    @Select("select sum(order_amount) order_amount from   product_status_2021 where toYYYYMMDD(stt)=#{date}; ")
    public BigDecimal getGmv(int date);

    @Select("select sum(order_amount) order_amount from   product_status_2021 where toYYYYMMDD(stt)=#{date}; \n" +
            "select spu_id,spu_name,sum(order_amount) order_amount  ,sum(order_ct) order_ct from   product_status_2021 where toYYYYMMDD(stt)=#{date}\n" +
            "group by spu_id,spu_name\n" +
            "having order_amount>0\n" +
            "order by order_amount desc \n" +
            " limit #{limit};")
    public List<ProductStats>   getProductStatsGroupBySpu(@Param("date") int date, @Param("limit") int limit);

@Select(" select\n" +
        "category3_id,\n" +
        "category3_name,\n" +
        "sum(order_amount) order_amount\n" +
        " from product_status_2021\n" +
        " where toYYYYMMDD(stt)=#{date}\n" +
        " group by category3_id,category3_name\n" +
        " having having order_amount>0\n" +
        "order by order_amount desc \n" +
        "limit #{limit}")
    public List<ProductStats>   getProductStatsGroupByCategory3(@Param("date")int date,@Param("limit")int limit);

@Select("select\n" +
        "tm_id,\n" +
        "tm_name,\n" +
        "sum(order_amount)\n" +
        "from product_status_2021\n" +
        "where toYYYYMMDD(stt)=#{date}\n" +
        "group by category3_id,category3_name\n" +
        "having having order_amount>0\n" +
        "order by order_amount desc \n" +
        "limit #{limit}")
    public List<ProductStats> getProductStatsByTrademark(@Param("date")int date,  @Param("limit") int limit);

}
