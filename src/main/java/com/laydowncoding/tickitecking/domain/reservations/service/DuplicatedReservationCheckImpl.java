package com.laydowncoding.tickitecking.domain.reservations.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicatedReservationCheckImpl implements DuplicatedReservationCheck {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean isDuplicated(String key, String value, Long expiredTime) {
        String script = "local keyExists = redis.call('exists', KEYS[1]) " +
            "local isAdded " +
            "if keyExists == 0 then " +
            "    redis.call('sadd', KEYS[1], ARGV[1]) " +
            "    redis.call('expire', KEYS[1], ARGV[2]) " +
            "    isAdded = 1 " +
            "else " +
            "    isAdded = redis.call('sadd', KEYS[1], ARGV[1]) " +
            "end " +
            "return tostring(isAdded)";
        DefaultRedisScript<String> luaScript = new DefaultRedisScript<>(script, String.class);
        List<String> keys = Collections.singletonList(key);

        return Objects.equals(redisTemplate.execute(luaScript, keys, value, expiredTime.toString()),
            "0");
    }

    @Override
    public void deleteValue(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }
}
