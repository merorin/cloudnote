package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.io.param.FcqCommonParam;
import merorin.cloud.cloudnote.fcq.io.param.FcqFunctionParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

/**
 * Description: 默认错误的队列处理者
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class ErrorFcqQueueProcessor extends AbstractFcqQueueProcessor {

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
