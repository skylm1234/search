package com.gejian.search.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.WatchHistoryDeleteAllDTO;
import com.gejian.search.common.dto.WatchHistoryDeleteDTO;
import com.gejian.search.common.dto.WatchHistoryQueryDTO;
import com.gejian.search.common.dto.WatchHistoryResponseDTO;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;

import java.util.List;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:08
 * @description：观看历史
 */
public interface WatchHistoryService {

    /**
     * 搜索视频
     * @param watchHistoryQueryDTO
     * @return
     */
     Page<OnlineSearchDTO> searchSubstance(WatchHistoryQueryDTO watchHistoryQueryDTO);


    /**
     * 搜索直播间
     * @param watchHistoryQueryDTO
     * @return
     */
     Page<OnlineSearchDTO> searchRoom(WatchHistoryQueryDTO watchHistoryQueryDTO);

    /**
     * 搜索
     * @param watchHistoryQueryDTO
     * @return
     */
    Page<WatchHistoryResponseDTO> search(WatchHistoryQueryDTO watchHistoryQueryDTO);

    /**
     * 删除播放记录
     * @param watchHistoryDeleteDTOs
     */
    void delete(List<WatchHistoryDeleteDTO> watchHistoryDeleteDTOs);

    /**
     * 删除播放记录
     * @param watchHistoryDeleteDTOs
     */
    void deleteAll(WatchHistoryDeleteAllDTO watchHistoryDeleteAllDTO);
}
