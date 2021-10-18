package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.KeywordStats;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-05-09 22:57
 */
public interface KeywordStatsService {
    public List<KeywordStats> selectKeywordStats(int date,int limit);
}
