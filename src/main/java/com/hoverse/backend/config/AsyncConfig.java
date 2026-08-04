package com.hoverse.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 03/08/2026
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "emailTaskExecutor")
    public Executor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // Lượng thread lúc rãnh
        executor.setMaxPoolSize(5); // Lượng thread lúc cao điểm
        executor.setQueueCapacity(50); // Cho phép 50 task đợi nếu 5 thread đều bận
        executor.setThreadNamePrefix("EmailSender-"); //Đặt tên pool
        executor.initialize();
        return executor;
    }
}
