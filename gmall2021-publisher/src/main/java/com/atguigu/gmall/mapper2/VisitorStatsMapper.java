package com.atguigu.gmall.mapper2;

import com.atguigu.gmall.bean2.VisitorStats;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-11 23:06
 */

public interface VisitorStatsMapper {
    @Select("SELECT \n" +
            "is_new,\n" +
            "SUM(uv_ct),\n" +
            "SUM(pv_ct),\n" +
            "SUM(sv_ct),\n" +
            "SUM(uj_ct) uj_ct,\n" +
            "SUM(dur_sum)\n" +
            "FROM visitor_stats_2021\n" +
            "WHERE toYYYYMMDD(stt)=#{date}\n" +
            "GROUP BY id_new")
    public List<VisitorStats> selectVisitorStatsByNewFlag(int date);
}
