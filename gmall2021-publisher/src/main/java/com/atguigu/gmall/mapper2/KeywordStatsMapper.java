package com.atguigu.gmall.mapper2;

import com.atguigu.gmall.bean2.KeywordStats;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-10 22:10
 * 根据关键词出现的类型分配不同的热度分数
 * 根据关键词的出现类型分配不同的热度分数
 * 	搜索关键词=10分
 * 	下单商品=5分
 * 	加入购物车=2分
 * 	点击商品=1分
 * 	其他=0分
 * clickhouse函数multilf类似于case when
 */
public interface KeywordStatsMapper {
    @Select("SELECT\n" +
            "keyword,\n" +
            "SUM(keyword_stats_2021.ct *\n" +
            "multiIf(\n" +
            "source='SEARCH',10,\n" +
            "source='ORDER',5,\n" +
            "source='CART',2,\n" +
            "source='CLICK',1,0\n" +
            ")\n" +
            ")ct\n" +
            "FROM \n" +
            "keyword_stats\n" +
            "WHERE \n" +
            "    toYYYYMMDD(stt)=#{date}\n" +
            "GROUP BY \n" +
            "    keyword\n" +
            "ORDER BY\n" +
            "    SUM(keyword_stats.ct)\n" +
            "LIMIT #{limit}            \n")
    public List<KeywordStats> selectKeywordStats(@Param("date") int date,@Param("limit") int limit);



}
