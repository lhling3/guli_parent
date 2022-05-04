package com.ling.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ling
 * @date 2022/5/4 13:41
 */
@SpringBootApplication
@ComponentScan("com.ling")
@MapperScan("com.ling.acl.mapper")
@EnableDiscoveryClient
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class,args);
    }
}
