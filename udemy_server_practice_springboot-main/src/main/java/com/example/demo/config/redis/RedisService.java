package com.example.demo.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate redisTemplate;

    // 데이터 삽입
    public void setValues(String token, String loginId) {
        log.info("Redis 데이터 삽입");
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(token, loginId, Duration.ofMinutes(3));
        // 3분 뒤 메모리에서 삭제
    }

    // key값으로 value 가져오기
    public String getValues(String token) {
        try {
            ValueOperations<String, String> values = redisTemplate.opsForValue();
            return values.get(token);
        }
        catch (Exception e) {
            return null;
        }

    }

    // key-value 삭제
    public void delValues(String token) {
        try {
            redisTemplate.delete(token.substring(7));
        }
        catch (Exception ignored) {

        }

    }
}
