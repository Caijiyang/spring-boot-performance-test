package org.felixcjy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 文档配置
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:41
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("多模块 API 文档")
                        .version("1.0")
                        .description("Spring Boot 2.7.18 + OpenAPI 3"));
    }
}
