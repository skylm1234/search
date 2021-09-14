package com.gejian.search.web.executor;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：lijianghuai
 * @date ：2021-09-13 16:42
 * @description：
 */

@Component
@Slf4j
public class EsAliveTask {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Scheduled(cron = "0 0/2 * * * *")
    public void keepESAlive() {
        try {
            restHighLevelClient.info(RequestOptions.DEFAULT);
            log.info("keep es alive");
        } catch (IOException e) {
            log.error("keep es alive error!" ,e);
        }
    }
}
