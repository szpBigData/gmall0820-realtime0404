package com.atguigu.gmall.mapper3;

import com.atguigu.gmall.bean3.VisitorStats;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-24 22:00
 */
public interface VisitorStatsMapper {
    @Select("select \n" +
            "is_new,\n" +
            "sum(uv_ct)  uv_ct,\n" +
            "sum(pv_ct)  pv_ct,\n" +
            "sum(sv_ct)  sv_ct,\n" +
            "sum(uj_ct)  uj_ct,\n" +
            "sum(dur_sum) dur_sum\n" +
            "from visitor_stats_2021\n" +
            "where toYYYYMMDD(stt)=#{date}\n" +
            "group by is_new\n")
    public List<VisitorStats>  gselectVisitorStatsByNewFlag(int date);

    @Select("select count(uv_ct) uv_ct from visitor_stats_2021 where toYYYYMMDD(stt)=#{date}")
    public Long  selectUv(int date);

    @Select("select count(pv_ct) pv_ct from visitor_stats_2021 where toYYYYMMDD(stt)=#{date}")
    public Long  selectPv(int date);

    @Select("select\n" +
            "sum(if(is_new='1',visitor_stats_2021.uv_ct,0)) new_uv,\n" +
            "toHour(stt) hr,\n" +
            "sum(visitor_stats_2021.uv_ct) uv_ct,\n" +
            "sum(pv_ct) pv_ct,\n" +
            "sum(uj_ct) uj_ct\n" +
            "from visitor_stats_2021\n" +
            "where toYYYYMMDD(stt)=#{date}\n" +
            "group by toHour(stt)")
    public List<VisitorStats> selectVisitorStatsByHour(int date);
}
