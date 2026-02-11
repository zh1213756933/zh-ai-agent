package com.zhou.zhaiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.zhou.zhaiagent", "com.zhou.common"})
@SpringBootApplication
public class ZhAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhAiAgentApplication.class, args);
    }

}
