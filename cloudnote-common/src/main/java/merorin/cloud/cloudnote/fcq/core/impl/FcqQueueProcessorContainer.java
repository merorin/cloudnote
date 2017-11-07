package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.core.FcqQueueProcessable;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;

/**
 * Description: fcq队列处理者的容器
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqQueueProcessorContainer implements FcqQueueProcessable {

    private static final Logger LOG = LoggerFactory.getLogger(FcqQueueProcessorContainer.class);

    private final FcqConfigContainer configContainer;

    public FcqQueueProcessorContainer(FcqConfigContainer configContainer) {
        this.configContainer = configContainer;
    }

    @Override
    public FcqProcessResult offer(FcqParam param) {
        return this.execFunc(param, processor -> processor.offer(param));
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        return this.execFunc(param, processor -> processor.poll(param));
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        return this.execFunc(param, processor -> processor.getFromHead(param));
    }

    @Override
    public FcqProcessResult findValue(FcqParam param) {
        return this.execFunc(param, processor -> processor.findValue(param));
    }

    @Override
    public FcqProcessResult removeValue(FcqParam param) {
        return this.execFunc(param, processor -> processor.removeValue(param));
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        return this.execFunc(param, processor -> processor.getAllElements(param));
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        return this.execFunc(param, processor -> processor.pollAllElements(param));
    }

    /**
     * 执行传入的方法
     * @param param 外界传入的参数
     * @param func 传入的函数
     * @return 执行结果
     */
    private FcqProcessResult execFunc(final FcqParam param, final Function<AbstractFcqQueueProcessor, FcqProcessResult> func) {
        return Optional.of(param)
                .map(this::doGetProcessor)
                .filter(this::isErrorProcessor)
                .map(func)
                .orElse(this.buildErrorResult(param.getFcqQueueName(), param.getFcqTypeName()));
    }

    /**
     * 获取对应的队列处理者
     * @param param 传入的参数
     * @return 得到的处理者
     */
    private AbstractFcqQueueProcessor doGetProcessor(final FcqParam param) {
        return this.configContainer.getProcessor(param.getFcqQueueName(), param.getFcqTypeName());
    }

    /**
     * 是否是错误的队列处理者
     * @param processor 传入的队列处理者
     * @return 判断结果
     */
    private boolean isErrorProcessor(final AbstractFcqQueueProcessor processor) {
        return Optional.ofNullable(processor)
                .map(item -> !(item instanceof ErrorFcqQueueProcessor))
                .orElse(false);
    }

    /**
     * 构造一个错误的返回结果
     * @param queueName 队列名
     * @param typeName 类型
     * @return 构造出来的结果
     */
    private FcqProcessResult buildErrorResult(final String queueName, final String typeName) {
        LOG.error("Fail to get processor with name, {}. The fcq type is {}.",queueName, typeName);

        return new FcqProcessResult();
    }
}
