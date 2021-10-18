package com.atguigu.gmall.service2;

import com.atguigu.gmall.bean2.KeywordStats;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-10 22:25
 */
public interface KeywordStatsService{
    public List<KeywordStats> selectKeywordStats(@Param("date") int date, @Param("limit") int limit);
}
