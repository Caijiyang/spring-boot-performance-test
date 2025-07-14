package org.felixcjy.controller.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 14:12
 */
@RestController
@RequestMapping("/test/security")
@Tag(name = "测试-Security 权限测试")
public class SecurityTestController {
    @GetMapping("/public")
    public String publicEndpoint() {
        return "这是一个公开端点，所有人都可以访问";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "这是一个受保护端点，你必须登录后才能访问";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "只有 ADMIN 角色可以访问";
    }

    @GetMapping("/common")
    public String commonEndpoint() {
        return "COMMON 和 ADMIN 都可以访问";
    }
}
