package merorin.cloud.cloudnote.redis.impl;

import merorin.cloud.cloudnote.bo.data.redis.RedisCacheParam;
import merorin.cloud.cloudnote.bo.result.redis.RedisCacheResult;
import merorin.cloud.cloudnote.redis.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * Description: redis缓存管理者实现
 *
 * @author guobin On date 2017/10/28.
 * @version 1.0
 * @since jdk 1.8
 */
public class RedisCacheManagerImpl implements RedisCacheManager {

    @Resource
    private StringRedisTemplate redisTemplate;

    @Override
    public <T> RedisCacheResult<T> saveValue(RedisCacheParam<T> request) {
        return null;
    }

    @Override
    public <T> RedisCacheResult<T> getValue(RedisCacheParam<T> request) {
        return null;
    }

    @Override
    public <T> RedisCacheResult<T> removeValue(RedisCacheParam<T> request) {
        return null;
    }

    @Override
    public <T> RedisCacheResult<T> listValues(RedisCacheParam<T> request) {
        return null;
    }


}
