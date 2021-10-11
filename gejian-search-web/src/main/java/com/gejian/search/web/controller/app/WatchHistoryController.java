package com.gejian.search.web.controller.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.common.core.util.R;
import com.gejian.search.common.dto.WatchHistoryDeleteDTO;
import com.gejian.search.common.dto.WatchHistoryQueryDTO;
import com.gejian.search.common.dto.WatchHistoryResponseDTO;
import com.gejian.search.web.service.WatchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:04
 * @description：播放历史
 */
@RestController
@RequestMapping("/app/watch/history")
@Api(value = "app-watch-history", tags = "视频/直播观看历史-App")
@ApiOperation(value = "视频搜索")
public class WatchHistoryController {

    @Autowired
    private WatchHistoryService watchHistoryService;

   /* @PostMapping("/video")
    @ApiOperation("视频历史列表")
    public R<Page<OnlineSearchDTO>> searchSubstance(@Valid @RequestBody  WatchHistoryQueryDTO watchHistoryQueryDTO){
        return R.ok(watchHistoryService.searchSubstance(watchHistoryQueryDTO));
    }

    @PostMapping("/room")
    @ApiOperation("直播历史记录")
    public R<Page<OnlineSearchDTO>> searchRoom(@Valid @RequestBody  WatchHistoryQueryDTO watchHistoryQueryDTO){
        return R.ok(watchHistoryService.searchRoom(watchHistoryQueryDTO));
    }*/

    @PostMapping("")
    @ApiOperation("播放历史记录")
    public R<Page<WatchHistoryResponseDTO>> search(@Valid @RequestBody  WatchHistoryQueryDTO watchHistoryQueryDTO){
        return R.ok(watchHistoryService.search(watchHistoryQueryDTO));
    }

    @PostMapping("/delete")
    @ApiOperation("删除播放历史记录,参数为空，则删除所有")
    public R<Void> delete(@Valid @RequestBody List<WatchHistoryDeleteDTO> watchHistoryDeleteDTOs){
        watchHistoryService.delete(watchHistoryDeleteDTOs);
        return R.ok();
    }
}
