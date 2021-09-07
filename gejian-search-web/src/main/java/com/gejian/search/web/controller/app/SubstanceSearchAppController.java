package com.gejian.search.web.controller.app;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.util.R;
import com.gejian.search.common.dto.HistorySearchDTO;
import com.gejian.search.common.dto.PopularSearchDTO;
import com.gejian.search.common.dto.SubstanceSearchDTO;
import com.gejian.search.web.service.RedisSearchService;
import com.gejian.search.web.service.SubstanceSearchService;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


/**
 * @Author:lijianghuai
 * @Date: 2021/8/4 16:13
 */
@RestController
@RequestMapping("/app/search")
@Api(value = "app-search-substance", tags = "视频搜索-App")
@ApiOperation(value = "视频搜索")
public class SubstanceSearchAppController {

    @Autowired
    private SubstanceSearchService substanceSearchService;

    @Autowired
    private RedisSearchService redisSearchService;

    @PostMapping("/substance")
    @ApiOperation("全局搜索视频，无需认证")
    public R<Page<OnlineSearchDTO>> search(@RequestBody @Valid SubstanceSearchDTO substanceSearchDTO) {
        return R.ok(substanceSearchService.search(substanceSearchDTO));
    }

    @PostMapping("/popular")
    @ApiOperation("热门搜索")
    public R<List<String>> popular(@RequestBody @Valid PopularSearchDTO popularSearchDTO) {
        Integer size = popularSearchDTO.getSize();
        return R.ok(redisSearchService.getHotSearchList(size));
    }

    @PostMapping("/history")
    @ApiOperation("获取历史搜索")
    public R<Set<String>> getHistory(
            @RequestBody @Valid HistorySearchDTO historySearchDTO) {
        return R.ok(redisSearchService.getHistorySearch(historySearchDTO.getSize(), historySearchDTO.getCurrent()));
    }

    @PostMapping("/delete/history")
    @ApiOperation("删除历史搜索")
    public R deleteHistory(@RequestParam("content") String content) {
        redisSearchService.deleteHistorySearch(content);
        return R.ok();
    }
}
