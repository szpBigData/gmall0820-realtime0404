package com.atguigu.gmall.service3;

import com.atguigu.gmall.bean3.KeywordStats;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-25 22:52
 */
public interface KeywordStatsService {
    public List<KeywordStats> selectKeywordStats(@Param("date") int date, @Param("limit") int limit);
}
