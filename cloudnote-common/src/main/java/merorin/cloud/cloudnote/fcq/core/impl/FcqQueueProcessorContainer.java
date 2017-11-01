package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.FcqQueueProcessable;
import merorin.cloud.cloudnote.fcq.io.param.FcqCommonParam;
import merorin.cloud.cloudnote.fcq.io.param.FcqFunctionParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;

/**
 * Description: fcq队列处理者的容器
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqQueueProcessorContainer implements FcqQueueProcessable {

    private final FcqConfigContainer configContainer;

    public FcqQueueProcessorContainer(FcqConfigContainer configContainer) {
        this.configContainer = configContainer;
    }

    @Override
    public <T> FcqProcessResult<T> pushForCommon(FcqCommonParam<T> param) {
        return null;
    }

    @Override
    public <T, R> FcqProcessResult<T> pushForFunction(FcqFunctionParam<T, R> param) {
        return null;
    }

    @Override
    public <T> FcqProcessResult<T> poll(FcqCommonParam<T> param) {
        return null;
    }

    @Override
    public <T> FcqProcessResult<T> getFromHead(FcqCommonParam<T> param) {
        return null;
    }

    @Override
    public <T> FcqProcessResult<T> findValue(FcqCommonParam<T> param) {
        return null;
    }

    @Override
    public <T> FcqProcessResult<T> removeValue(FcqCommonParam<T> param) {
        return null;
    }

    @Override
    public <T> FcqProcessResult<T> getAllElements(FcqCommonParam<T> param) {
        return null;
    }
}
