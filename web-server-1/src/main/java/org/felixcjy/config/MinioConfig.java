package org.felixcjy.config;

import io.minio.MinioClient;
import org.felixcjy.properties.MinioProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:45
 */
@Configuration
public class MinioConfig {
    @Bean
    public MinioClient minioClient(MinioProperties properties) {
        return MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
    }
}

