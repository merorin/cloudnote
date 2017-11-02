package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

/**
 * Description: 默认的fcq错误执行者实例
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class ErrorFcqDataExecutor extends AbstractFcqDataExecutor {

    /**
     * 错误执行者对应的队列名
     */
    private static final String ERROR_QUEUE_NAME = "errorDataExecutor";

    /**
     * 错误的队列类型
     */
    private static final String ERROR_QUEUE_TYPE = "errorFcqType";

    public ErrorFcqDataExecutor() {
        super(ERROR_QUEUE_NAME, ERROR_QUEUE_TYPE, false);
    }

    @Override
    public <T> FcqProcessResult<T> read() {
        return null;
    }

    @Override
    public void run() {

    }
}
