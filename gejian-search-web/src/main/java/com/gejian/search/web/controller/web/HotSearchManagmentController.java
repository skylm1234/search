package com.gejian.search.web.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.PopularSearchBackendQueryDTO;
import com.gejian.search.common.dto.PopularSearchBackendResultDTO;
import com.gejian.search.web.service.SearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ：lijianghuai
 * @date ：2021-10-11 10:35
 * @description：热搜管理
 */
@RestController
@RequestMapping("/web/hotSearch")
@Api(value = "hotSearch-web", tags = "热搜管理-Web")
public class HotSearchManagmentController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    @PostMapping("/statistics")
    @ApiOperation("热搜词统计")
    public Page<PopularSearchBackendResultDTO> hotSearchWordsCount(@Valid @RequestBody PopularSearchBackendQueryDTO popularSearchBackendQueryDTO){
        return searchHistoryService.queryPopularWords(popularSearchBackendQueryDTO);
    }
}
