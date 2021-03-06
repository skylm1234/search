package com.gejian.search.web;

import com.gejian.common.feign.annotation.EnablePigFeignClients;
import com.gejian.common.security.annotation.EnableGeJianResourceServer;
import com.gejian.common.swagger.annotation.EnablePigSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhouqiang
 */
@EnablePigSwagger2
@EnableGeJianResourceServer
@EnablePigFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }


}
