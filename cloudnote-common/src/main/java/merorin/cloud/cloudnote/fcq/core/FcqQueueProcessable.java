package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

/**
 * Description: fcq队列的可处理化接口
 *              这个接口及其对应的实现类专门用来处理队列中数据的读取
 *              这个接口是fcq的核心,专门用来管理fcq队列
 *              {@link FcqDataExecutable} 均需要通过这个接口来实现数据的写与读取
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
     * @return 处理成功或者失败
     */
    FcqProcessResult offer(FcqParam param);

    /**
     * 从队列中弹出一份数据
     * @param param 封装的fcq数据
     * @return 处理结果
     */
    FcqProcessResult poll(FcqParam param);

    /**
     * 从队列首开始获取n条数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    FcqProcessResult getFromHead(FcqParam param);

    /**
     * 获取一条特定的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    FcqProcessResult findValue(FcqParam param);

    /**
     * 删除一条特定的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    FcqProcessResult removeValue(FcqParam param);

    /**
     * 获取队列中所有的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    FcqProcessResult getAllElements(FcqParam param);

    /**
     * 弹出队列中所有的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    FcqProcessResult pollAllElements(FcqParam param);

    /**
     * 将元素放到队列首位
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    FcqProcessResult putToHead(FcqParam param);
}
