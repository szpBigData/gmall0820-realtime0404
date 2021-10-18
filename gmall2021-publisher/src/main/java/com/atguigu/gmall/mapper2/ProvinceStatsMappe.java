package com.atguigu.gmall.mapper2;

import com.atguigu.gmall.bean2.ProvinceStats;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-13 6:54
 */
public interface ProvinceStatsMappe {
    @Select("select\n" +
            "province_name,\n" +
            "sum(order_amount) order_amount\n" +
            "from province_stats_2021\n" +
            "where \n" +
            "toYYYYMMDD(stt)=#{date}\n" +
            "group by province_id,province_name")
    public List<ProvinceStats> selectProvinceStats(int date);
}
