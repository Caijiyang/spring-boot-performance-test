package org.felixcjy.infrastructure.service;

/**
 * Redis 服务接口
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:51
 */
public interface RedisService {
    void setValue(String key, String value);

    String getValue(String key);

    boolean deleteKey(String key);

    boolean setExpire(String key, long seconds);

    /** 可选：带过期时间的设置方法 */
    void setValueWithExpire(String key, String value, long seconds);
}
