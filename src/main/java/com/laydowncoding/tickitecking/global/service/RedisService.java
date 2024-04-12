package com.laydowncoding.tickitecking.global.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;

  // 만료시간 설정 -> 자동 삭제
  @Transactional
  public void setValuesWithTimeout(String key, String value, long timeout) {
    redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.DAYS);
  }

  @Transactional
  public void deleteValues(String key) {
    redisTemplate.delete(key);
  }

  public boolean existsByKey(String key) {
    return redisTemplate.opsForValue().getOperations().hasKey(key);
  }

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
