package com.gejian.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gejian.search.common.dto.HistorySearchBackendQueryDTO;
import com.gejian.search.common.dto.HistorySearchBackendResultDTO;
import com.gejian.search.common.dto.PopularSearchBackendResultDTO;
import com.gejian.search.common.dto.PopularSearchBackendQueryDTO;
import com.gejian.search.web.service.HistorySearchBackendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：lijianghuai
 * @date ：2021-09-08 10:28
 * @description：
 */

@DisplayName("service测试")
public class HistoryServiceTest extends BaseTest{

    @Autowired
    private HistorySearchBackendService historySearchBackendService;

    @Test
    public void should_success_when_insert(){
        String content = "要说什么是 JUnit 5,首先就得聊下 Java 单元测试框架 ";
        historySearchBackendService.insert(content);
    }

    @Test
    public void should_success_when_query_popular(){
        PopularSearchBackendQueryDTO popularBackendSearchDTO = new PopularSearchBackendQueryDTO();
        popularBackendSearchDTO.setContent("美国");
        //popularBackendSearchDTO.setCurrent(5);
        //popularBackendSearchDTO.setStartedAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(1631000694), ZoneOffset.ofHours(8)));
        //popularBackendSearchDTO.setStartedAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(1631085371), ZoneOffset.ofHours(8)));

        Page<PopularSearchBackendResultDTO> query = historySearchBackendService.queryPopular(popularBackendSearchDTO);
        System.out.println(query.getTotal());
        query.getRecords().forEach(recored -> {
            System.out.println(recored.getKeyword() + ":" + recored.getCount());
        });
    }

    @Test
    public void should_success_when_query_history(){
        HistorySearchBackendQueryDTO historySearchBackendQueryDTO = new HistorySearchBackendQueryDTO();
        historySearchBackendQueryDTO.setContent("伊拉克");
        Page<HistorySearchBackendResultDTO> query = historySearchBackendService.queryHistorySearch(historySearchBackendQueryDTO);
        System.out.println(query.getTotal());
        query.getRecords().forEach(recored -> {
            System.out.println(recored.getContent());
        });
    }
}
