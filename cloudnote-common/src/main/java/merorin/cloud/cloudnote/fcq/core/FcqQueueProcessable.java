package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.io.param.FcqCommonParam;
import merorin.cloud.cloudnote.fcq.io.param.FcqFunctionParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

/**
 * Description: fcq队列的可处理化接口
 *              这个接口及其对应的实现类专门用来处理队列中数据的读取
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 * @version 1.0
 */
public interface FcqQueueProcessable {

    /**
     * 提交一份数据到队列中
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @return 处理成功或者失败
     */
    <T> FcqProcessResult<T> pushForCommon(FcqCommonParam<T> param);

    /**
     * 提交一份带函数的数据到队列中
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @param <R> 返回的结果类型
     * @return 处理成功或者失败
     */
    <T, R> FcqProcessResult<T> pushForFunction(FcqFunctionParam<T, R> param);

    /**
     * 从队列中弹出一份数据
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @return 处理成功或者失败
     */
    <T> FcqProcessResult<T> poll(FcqCommonParam<T> param);

    /**
     * 从队列首开始获取n条数据
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @return 处理成功或者失败
     */
    <T> FcqProcessResult<T> getFromHead(FcqCommonParam<T> param);

    /**
     * 获取一条特定的数据
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @return 处理成功或者失败
     */
    <T> FcqProcessResult<T> findValue(FcqCommonParam<T> param);

    /**
     * 删除一条特定的数据
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @return 处理成功或者失败
     */
    <T> FcqProcessResult<T> removeValue(FcqCommonParam<T> param);

    /**
     * 获取队列中所有的数据
     * @param param 封装的数据
     * @param <T> 封装的核心数据类型
     * @return 处理成功或者失败
     */
    <T> FcqProcessResult<T> getAllElements(FcqCommonParam<T> param);
}
