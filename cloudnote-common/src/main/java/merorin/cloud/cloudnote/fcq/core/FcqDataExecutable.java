package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

/**
 * Description: fcq可执行化接口,这个接口的实现类专门用来从队列中获取数据并执行
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public interface FcqDataExecutable {

    /**
     * 由外界发起的读取请求
     * @param <T> 读取的核心数据类型
     * @return 处理结果
     */
    <T> FcqProcessResult<T> read();

    /**
     * 此方法被调用时会启动一条{@code Runnable}守护线程
     * 线程执行的方法即为该方法中的表达式
     */
    void run();
}
