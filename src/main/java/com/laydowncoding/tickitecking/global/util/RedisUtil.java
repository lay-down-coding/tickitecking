package com.laydowncoding.tickitecking.global.util;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public String addSet(String key, String value, Long expiredTime) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("local keyExists = redis.call('exists', KEYS[1]) ");
        stringBuffer.append("local isAdded ");
        stringBuffer.append("if keyExists == 0 then ");
        stringBuffer.append("    redis.call('sadd', KEYS[1], ARGV[1]) ");
        stringBuffer.append("    redis.call('expire', KEYS[1], ARGV[2]) ");
        stringBuffer.append("    isAdded = 1 ");
        stringBuffer.append("else ");
        stringBuffer.append("    isAdded = redis.call('sadd', KEYS[1], ARGV[1]) ");
        stringBuffer.append("end ");
        stringBuffer.append("return tostring(isAdded)");
        String script = stringBuffer.toString();
        DefaultRedisScript<String> luaScript = new DefaultRedisScript<>(script, String.class);
        List<String> keys = Collections.singletonList(key);

        return redisTemplate.execute(luaScript, keys, value, expiredTime.toString());
    }

    public void deleteValue(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }
}
