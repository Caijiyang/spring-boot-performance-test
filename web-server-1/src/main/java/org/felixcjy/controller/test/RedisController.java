package org.felixcjy.controller.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.felixcjy.infrastructure.service.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 模拟 Redis 操作的 Controller
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:54
 */
@RestController
@RequestMapping("/test/redis")
@AllArgsConstructor
@Tag(name = "测试-Redis 操作接口")
public class RedisController {
    private final RedisService redisService;

    @PostMapping("/set")
    @Operation(summary = "设置值")
    public ResponseEntity<String> setValue(@RequestParam String key,
                                           @RequestParam String value) {
        redisService.setValue(key, value);
        return ResponseEntity.ok("Value set successfully");
    }

    @GetMapping("/get")
    @Operation(summary = "获取值")
    public ResponseEntity<?> getValue(@RequestParam String key) {
        String value = redisService.getValue(key);
        if (value == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Key not found");
        }
        return ResponseEntity.ok(value);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除值")
    public ResponseEntity<String> deleteKey(@RequestParam String key) {
        boolean deleted = redisService.deleteKey(key);
        return deleted ?
                ResponseEntity.ok("Key deleted successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Key not found");
    }

    @PostMapping("/expire")
    @Operation(summary = "设置带过期时间的值")
    public ResponseEntity<String> setExpire(@RequestParam String key,
                                            @RequestParam long seconds) {
        boolean success = redisService.setExpire(key, seconds);
        return success ?
                ResponseEntity.ok("Expiration set successfully") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Key not found");
    }
}
