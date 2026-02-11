package com.zhou.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(getApiInfo());
    }

    private Info getApiInfo() {
        return new Info().title("小周的Ai Agent").description("个人学习作品")
                .contact(new Contact().name("小周").email("1213756933@qq.com")
                );
    }

}
