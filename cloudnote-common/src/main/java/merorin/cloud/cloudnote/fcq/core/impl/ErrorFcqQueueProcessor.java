package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
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
    public FcqProcessResult offer(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult findValue(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult removeValue(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在ErrorFcqDataExecutor中尚未实现.");

        return result;
    }
}
