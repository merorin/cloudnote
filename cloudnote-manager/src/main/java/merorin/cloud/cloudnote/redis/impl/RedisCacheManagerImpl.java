package merorin.cloud.cloudnote.redis.impl;

import merorin.cloud.cloudnote.redis.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Description: redis缓存管理者实现
 *
 * @author guobin On date 2017/10/28.
 * @version 1.0
 * @since jdk 1.8
 */
public class RedisCacheManagerImpl<T> implements RedisCacheManager<T> {

    @Resource
    private RedisTemplate<String,T> redisTemplate;
}
