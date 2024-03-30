package com.sini.mysns.api.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ViewCountService {
    private final RedisTemplate<String, String> redisTemplate;

    public void incrementViewCount(Long postId)
    {
        redisTemplate.opsForZSet()
                .incrementScore("view-count", String.valueOf(postId), 1);
    }

    public Long getViewCount(Long postId) {
        Double score = redisTemplate.opsForZSet().score("view-count", String.valueOf(postId));
        return score != null ? score.longValue() : 0L;
    }

}
