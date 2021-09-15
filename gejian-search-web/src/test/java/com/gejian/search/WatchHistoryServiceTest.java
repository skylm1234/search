package com.gejian.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.WatchHistoryQueryDTO;
import com.gejian.search.web.service.WatchHistoryService;
import com.gejian.substance.client.dto.online.app.view.OnlineSearchDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：lijianghuai
 * @date ：2021-09-08 10:28
 * @description：
 */

@DisplayName("service测试")
public class WatchHistoryServiceTest extends BaseTest{

    @Autowired
    private WatchHistoryService watchHistoryService;


    @Test
    public void should_success_when_query_history(){
        WatchHistoryQueryDTO watchHistoryQueryDTO = new WatchHistoryQueryDTO();
        watchHistoryQueryDTO.setCurrent(1);
        watchHistoryQueryDTO.setSize(10);
        Page<OnlineSearchDTO> onlineSearchDTOPage = watchHistoryService.searchSubstance(watchHistoryQueryDTO);
        Assertions.assertTrue(onlineSearchDTOPage.getTotal() > 0);
    }
}
