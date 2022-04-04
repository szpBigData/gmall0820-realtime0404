package com.atguigu.gmall.service3.impl;

import com.atguigu.gmall.bean3.KeywordStats;
import com.atguigu.gmall.mapper3.KeywordStatsMapper;
import com.atguigu.gmall.service3.KeywordStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-25 22:53
 */
@Service
public class KeywordStatsServiceImpl implements KeywordStatsService {
    @Autowired
    KeywordStatsMapper keywordStatsMapper;
    @Override
    public List<KeywordStats> selectKeywordStats(int date, int limit) {
        return keywordStatsMapper.selectKeywordStats(date,limit);
    }
}
