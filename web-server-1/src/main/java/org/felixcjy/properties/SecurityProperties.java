package org.felixcjy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:07
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    /** 开放访问路径 */
    private List<String> permitPaths;
}
