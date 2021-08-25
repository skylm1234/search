package com.gejian.search.web;

import com.gejian.common.feign.annotation.EnablePigFeignClients;
import com.gejian.common.security.annotation.EnableGeJianResourceServer;
import com.gejian.common.swagger.annotation.EnablePigSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnablePigSwagger2
@EnableGeJianResourceServer
@EnablePigFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class GeJianSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeJianSearchApplication.class, args);
	}

}
