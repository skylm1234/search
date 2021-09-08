package com.gejian.search.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.PopularBackendResultDTO;
import com.gejian.search.common.dto.PopularBackendSearchDTO;

/**
 * @author ：lijianghuai
 * @date ：2021-09-07 18:02
 * @description：热门搜索
 */
public interface PopularSearchBackendService {

    /**
     * 插入
     * @param content 搜索输入的文本
     */
    void insert(String content);

    Page<PopularBackendResultDTO> query(PopularBackendSearchDTO popularBackendSearchDTO);

}
