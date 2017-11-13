package merorin.cloud.cloudnote.redis;

import merorin.cloud.cloudnote.bo.data.redis.RedisCacheParam;
import merorin.cloud.cloudnote.bo.result.redis.RedisCacheResult;

/**
 * Description: redis缓存管理者
 *
 * @author guobin On date 2017/10/28.
 * @version 1.0
 * @since jdk 1.8
 */
public interface RedisCacheManager {

    /**
     * 保存一个对象
     * @param request 请求
     * @return 处理结果
     */
    <T> RedisCacheResult<T> saveValue(RedisCacheParam<T> request);

    /**
     * 获取一个对象
     * @param request 请求
     * @return 处理结果,值存放在result的{@code value}中
     */
    <T> RedisCacheResult<T> getValue(RedisCacheParam<T> request);

    /**
     * 删除一个对象
     * @param request 请求
     * @return 处理结果
     */
    <T> RedisCacheResult<T> removeValue(RedisCacheParam<T> request);

    /**
     * 根据条件来以list的形式展示所有符合条件的数据
     * @param request 请求
     * @return 处理结果,值存放在result的{@code valueList}中
     */
    <T> RedisCacheResult<T> listValues(RedisCacheParam<T> request);



}
