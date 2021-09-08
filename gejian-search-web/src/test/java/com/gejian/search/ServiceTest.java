package com.gejian.search;

import com.gejian.search.web.service.PopularSearchBackendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：lijianghuai
 * @date ：2021-09-08 10:28
 * @description：
 */

@DisplayName("service测试")
public class ServiceTest extends BaseTest{

    @Autowired
    private PopularSearchBackendService popularSearchBackendService;

    @Test
    public void should_success_when_insert(){
        String content = "要说什么是 JUnit 5,首先就得聊下 Java 单元测试框架 ";
        //popularSearchBackendService.insert(content);
    }
}
