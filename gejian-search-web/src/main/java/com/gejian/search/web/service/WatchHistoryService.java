package com.gejian.search.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.WatchHistoryQueryDTO;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:08
 * @description：
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
}
