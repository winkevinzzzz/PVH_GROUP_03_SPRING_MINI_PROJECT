package org.example.spring_boot_mini_project.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("org.example.spring_boot_mini_project.repository")
public class MyBatisConfig {

    @Bean
    public typeHandler uuidTypeHandler() {
        return new typeHandler();
    }
}



