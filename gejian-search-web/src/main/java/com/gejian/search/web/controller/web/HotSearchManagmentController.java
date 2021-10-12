package com.gejian.search.web.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.util.R;
import com.gejian.search.common.dto.*;
import com.gejian.search.web.service.HotSearchService;
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
    @Autowired
    private HotSearchService hotSearchService;

    @PostMapping("/statistics")
    @ApiOperation("热搜词统计")
    public Page<PopularSearchBackendResultDTO> hotSearchWordsCount(@Valid @RequestBody PopularSearchBackendQueryDTO popularSearchBackendQueryDTO){
        return searchHistoryService.queryPopularWords(popularSearchBackendQueryDTO);
    }

    @PostMapping("/save")
    @ApiOperation("添加热搜话题")
    public R saveHotSearch(@Valid @RequestBody HotSearchDTO hotSearchDTO){
        return R.ok(hotSearchService.saveHotSearch(hotSearchDTO));
    }

    @PostMapping("/update")
    @ApiOperation("修改热搜话题")
    public R updateHotSearch(@Valid @RequestBody HotSearchUpdateDTO hotSearchUpdateDTO){
        return R.ok(hotSearchService.updateHotSearch(hotSearchUpdateDTO));
    }

    @PostMapping("/delete")
    @ApiOperation("删除热搜话题")
    public R deleteHotSearch(@Valid @RequestBody HotSearchDeleteDTO hotSearchDeleteDTO){
        return R.ok(hotSearchService.deleteHotSearch(hotSearchDeleteDTO));
    }

    @PostMapping("/stick")
    @ApiOperation("置顶热搜话题")
    public R stickHotSearch(@Valid @RequestBody HotSearchStickDTO hotSearchStickDTO){
        return R.ok(hotSearchService.stickHotSearch(hotSearchStickDTO));
    }

    @PostMapping("/page")
    @ApiOperation("热搜话题分页查询")
    public R<IPage<HotSearchResponseDTO>> pageHotSearch(@Valid @RequestBody HotSearchQueryDTO hotSearchQueryDTO){
        return  R.ok(hotSearchService.pageHotSearch(hotSearchQueryDTO));
    }
}
