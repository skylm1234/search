package com.gejian.search.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.HistorySearchBackendQueryDTO;
import com.gejian.search.common.dto.HistorySearchBackendResultDTO;
import com.gejian.search.common.dto.PopularSearchBackendResultDTO;
import com.gejian.search.common.dto.PopularSearchBackendQueryDTO;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 18:02
 * @description：热门搜索
 */
public interface HistorySearchBackendService {

    /**
     * 插入
     * @param content 搜索输入的文本
     */
    void insert(String content);

    /**
     * 查询热门词汇
     * @param popularBackendSearchDTO
     * @return
     */
    Page<PopularSearchBackendResultDTO> queryPopular(PopularSearchBackendQueryDTO popularBackendSearchDTO);

    /**
     * 查询历史搜索记录
     * @param historySearchBackendQueryDTO
     * @return
     */
    Page<HistorySearchBackendResultDTO> queryHistorySearch(HistorySearchBackendQueryDTO historySearchBackendQueryDTO);

}
