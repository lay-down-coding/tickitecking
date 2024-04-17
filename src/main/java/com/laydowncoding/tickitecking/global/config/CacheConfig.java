package com.laydowncoding.tickitecking.global.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CONCERT_CACHE = "concertCache";
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CONCERT_CACHE);
    }

    //매 6시간마다 콘서트 캐시 제거
    @CacheEvict(allEntries = true, value = {CONCERT_CACHE})
    @Scheduled(cron = "0 0 */6 * * *")
    public void cacheEvict() {
    }
}
