package com.test.encrypted.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfiguration {

    @Bean
    public RateLimiter drawMatchesDataRateLimiter(){
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(0))
                .limitRefreshPeriod(Duration.ofSeconds(3))
                .limitForPeriod(1)
                .build();
        System.out.println("DrawMatchesDataRateLimiter RateLimiter Config is running");
        return RateLimiter.of("drawMatchesDataRateLimiter",rateLimiterConfig);
    }

    @Bean
    public RateLimiter sendDataToBackend(){
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(0))
                .limitRefreshPeriod(Duration.ofSeconds(3))
                .limitForPeriod(1)
                .build();
        return RateLimiter.of("sendDataToBackend",rateLimiterConfig);
    }
}
