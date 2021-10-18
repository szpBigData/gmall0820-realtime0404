package com.atguigu.gmall.service2.impl;

import com.atguigu.gmall.bean2.KeywordStats;
import com.atguigu.gmall.mapper2.KeywordStatsMapper;
import com.atguigu.gmall.service2.KeywordStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunzhipeng
 * @create 2021-10-11 22:42
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
