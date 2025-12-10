package com.yanzhengma.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String CAPTCHA_PREFIX = "captcha:";
    public void set(String key, String value, long timeoutMinutes) {
        redisTemplate.opsForValue().set(key, value, timeoutMinutes, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(CAPTCHA_PREFIX + key);
    }

    public void delete(String key) {
        redisTemplate.delete(CAPTCHA_PREFIX + key);
    }
}