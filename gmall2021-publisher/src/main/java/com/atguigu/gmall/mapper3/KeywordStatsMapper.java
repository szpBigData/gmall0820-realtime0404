package com.atguigu.gmall.mapper3;

import com.atguigu.gmall.bean3.KeywordStats;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-25 22:49
 */
public interface KeywordStatsMapper {
    @Select("select keyword,  \n" +
            "sum(keyword_stats_2021.ct * \n" +
            "multiIf(\n" +
            "source='SEARCH',10,\n" +
            "source='ORDER',5,\n" +
            "source='CART',2,\n" +
            "source='CLICK',1,0\n" +
            ")) ct  \n" +
            "from \n" +
            "keyword_stats \n" +
            "where \n" +
            "toYYYYMMDD(stt)=#{date}\n" +
            "group by \n" +
            "keyword \n" +
            "order by \n" +
            "sum(keyword_stats.ct) \n" +
            "limit #{limit};\n")
    public List<KeywordStats> selectKeywordStats(@Param("date") int date, @Param("limit") int limit);
}
