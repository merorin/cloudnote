package merorin.cloud.cloudnote.fcq.core.impl;

import com.alibaba.fastjson.JSON;
import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.core.FcqQueueProcessable;
import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;
import merorin.cloud.cloudnote.validate.core.FuncValidator;
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
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .notNull(FcqParam::getDataKey, "需要一个数据唯一标识")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.offer(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.poll(param));
                })
                .error(this::ifValidateFailed)
                .withValidation();
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .on(item -> item.getReadCount() < 1, "读取次数至少为1.")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.getFromHead(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
    }

    @Override
    public FcqProcessResult findValue(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .notNull(FcqParam::getDataKey, "需要一个数据唯一标识")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.findValue(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
    }

    @Override
    public FcqProcessResult removeValue(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .notNull(FcqParam::getDataKey, "需要一个数据唯一标识")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.removeValue(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
        
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.getAllElements(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.pollAllElements(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
    }

    @Override
    public FcqProcessResult putToHead(FcqParam param) {
        return FuncValidator.of(param)
                .notNull(FcqParam::getFcqTypeName, "必须指定队列类型")
                .notNull(FcqParam::getFcqQueueName, "必须指定队列名")
                .notNull(FcqParam::getDataKey, "需要一个数据唯一标识")
                .success(item -> {
                    return this.execFunc(item, processor -> processor.putToHead(item));
                })
                .error(this::ifValidateFailed)
                .withValidation();
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
        return FcqProcessResult.error(FcqResultConstant.Message.ERROR);
    }

    /**
     * 如果验证失败则返回结果
     * @param validator 验证器
     * @return 验证失败返回的结果
     */
    private FcqProcessResult ifValidateFailed(FuncValidator validator) {
        return FcqProcessResult.error(JSON.toJSONString(validator.getErrors()));
    }
}
