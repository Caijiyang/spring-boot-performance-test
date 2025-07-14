package org.felixcjy.infrastructure.service.impl;

import lombok.AllArgsConstructor;
import org.felixcjy.infrastructure.service.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis 服务实现类
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:51
 */
@Service
@AllArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final StringRedisTemplate redisTemplate;

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public boolean setExpire(String key, long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void setValueWithExpire(String key, String value, long seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }
}
