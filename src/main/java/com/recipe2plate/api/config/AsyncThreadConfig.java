package com.recipe2plate.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class AsyncThreadConfig {

    @Bean
    public Executor threadPoolExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setThreadNamePrefix("background-thread");
        return taskExecutor;
    }
}
