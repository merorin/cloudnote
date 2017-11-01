package merorin.cloud.cloudnote.redis.impl;

import merorin.cloud.cloudnote.bo.data.redis.RedisCacheParam;
import merorin.cloud.cloudnote.bo.result.redis.RedisCacheResult;
import merorin.cloud.cloudnote.dao.user.UserDao;
import merorin.cloud.cloudnote.fcq.io.param.FcqFunctionParam;
import merorin.cloud.cloudnote.po.data.user.UserPO;
import merorin.cloud.cloudnote.po.result.CommonDomainResult;
import merorin.cloud.cloudnote.redis.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.function.Function;

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

    @Resource
    private UserDao userDao;

    @Override
    public RedisCacheResult<T> saveValue(RedisCacheParam<T> request) {
        UserPO user = new UserPO();
        Function<UserPO, CommonDomainResult<UserPO>> mapper = this.userDao::saveUser;
        FcqFunctionParam<UserPO, CommonDomainResult<UserPO>> param = new FcqFunctionParam<>();

        return null;
    }

    @Override
    public RedisCacheResult<T> getValue(RedisCacheParam<T> request) {
        return null;
    }

    @Override
    public RedisCacheResult<T> removeValue(RedisCacheParam<T> request) {
        return null;
    }

    @Override
    public RedisCacheResult<T> listValuesByList(RedisCacheParam<T> request) {
        return null;
    }
}
