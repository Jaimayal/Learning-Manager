package com.jaimayal.learningmanager.presentation;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LayoutThymeleaf {
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
