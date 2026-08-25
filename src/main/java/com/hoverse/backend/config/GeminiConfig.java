package com.hoverse.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/08/2026
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiConfig {
    private String url;
    private String key;
    private String model;
    private String method;
}
