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

    @Override
    public <T> FcqProcessResult<T> read() {
        return null;
    }

    @Override
    protected void runnableCall() {

    }
}
