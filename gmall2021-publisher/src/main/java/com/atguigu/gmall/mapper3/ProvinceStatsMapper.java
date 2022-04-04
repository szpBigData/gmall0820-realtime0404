package com.atguigu.gmall.mapper3;

import com.atguigu.gmall.bean3.ProvinceStats;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-24 19:30
 */
public interface ProvinceStatsMapper {
    @Select("select\n" +
            "province_id,\n" +
            "province_name,\n" +
            "sum(order_amount)   order_amount\n" +
            "from province_status_2021\n" +
            "where toYYYYMMDD(ts)=#{date}\n" +
            "group by province_id,province_name\n" +
            ";")
  public List<ProvinceStats>  selectProvinceStats(int date);
}
