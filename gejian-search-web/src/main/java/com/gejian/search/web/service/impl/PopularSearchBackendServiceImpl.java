package com.gejian.search.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.PopularBackendResultDTO;
import com.gejian.search.common.dto.PopularBackendSearchDTO;
import com.gejian.search.common.index.SearchHistoryIndex;
import com.gejian.search.web.service.PopularSearchBackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 11:51
 * @description：
 */
@Component
public class PopularSearchBackendServiceImpl implements PopularSearchBackendService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void insert(String content) {
        SearchHistoryIndex historyIndex = SearchHistoryIndex.builder().content(content).createTime(LocalDateTime.now()).build();
        elasticsearchRestTemplate.save(historyIndex);
    }

    @Override
    public Page<PopularBackendResultDTO> query(PopularBackendSearchDTO popularBackendSearchDTO) {
        return null;
    }
}
