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
     * 这个函数里为外界主动发起的队列read请求并进行处理
     * 例如外界将数据poll出去并移交其他地方处理,那么方法均写在这个方法中
     * @return 处理结果
     */
    FcqProcessResult read();

    /**
     * 此方法被调用时会启动一条{@code Runnable}守护线程
     * 线程执行的方法即为该方法中的表达式
     */
    void run();
}
