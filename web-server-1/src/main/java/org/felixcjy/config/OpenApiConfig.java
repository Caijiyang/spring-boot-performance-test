package org.felixcjy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
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
                        .contact(new Contact().name("蔡济阳(Felix)"))
                        .description("本接口文档由后端团队（我）倾情奉献，使用前请深呼吸三秒，准备迎接秩序与逻辑的盛宴。")
                        .version("1.0"));
    }
}
