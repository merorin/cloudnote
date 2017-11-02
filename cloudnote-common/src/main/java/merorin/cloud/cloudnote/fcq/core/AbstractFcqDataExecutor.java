package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.core.impl.FcqQueueProcessorContainer;
import merorin.cloud.cloudnote.fcq.io.common.FcqFuncResponse;
import merorin.cloud.cloudnote.fcq.io.param.FcqFunctionParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

import java.util.Optional;

/**
 * Description: fcq队列读者的抽象类,所有读者都必须继承这个类
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public abstract class AbstractFcqDataExecutor implements FcqDataExecutable {

    /**
     * fcq队列的处理者
     */
    protected FcqQueueProcessorContainer processorContainer;

    /**
     * 队列名字
     */
    private final String fcqQueueName;

    /**
     * 队列类型
     */
    private final String fcqQueueType;

    /**
     * 用来判断是否要启动一个后台线程
     */
    private final boolean needThread;

    public AbstractFcqDataExecutor(String fcqQueueName, String fcqQueueType, boolean needThread) {
        this.fcqQueueName = fcqQueueName;
        this.fcqQueueType = fcqQueueType;
        this.needThread = needThread;
    }

    /**
     * 由外界发起的读取请求
     * @param <T> 读取的核心数据类型
     * @return 处理结果
     */
    @Override
    public abstract <T> FcqProcessResult<T> read();

    /**
     * 此方法被调用时会启动一条守护线程来执行该方法
     * 所有需要启动守护线程的执行者都必须实现这个方法
     */
    @Override
    public abstract void run();

    /**
     * 调用传入的function
     * @param param 传入的参数,不能为空
     * @param <T> 核心数据类型
     * @param <R> 调用函数后返回的结果
     * @return 返回的是一个{@code R}的JSON字符串以及函数处理返回码.
     *          当param参数有问题时,将返回{@code FcqFuncResponse.Msg.INVALID_PARAM}
     *          当调用函数返回的结果为空时,将返回{@code FcqFuncResponse.Msg.EMPTY_MESSAGE}
     * @throws NullPointerException 如果param为null
     */
    protected <T, R> FcqFuncResponse execFunction(final FcqFunctionParam<T, R> param) {

        return Optional.of(param).map(FcqFunctionParam::getFuncType)
                .map(funcType -> {
                    final FcqFuncResponse fcqFuncResponse;
                    switch (funcType) {
                        case FUNCTION:
                            fcqFuncResponse = this.execMapper(param);
                            break;
                        case CONSUMER:
                            fcqFuncResponse = this.execConsumer(param);
                            break;
                        case SUPPLIER:
                            fcqFuncResponse = this.execSupplier(param);
                            break;
                        case PERFORMER:
                            fcqFuncResponse = this.execPerformer(param);
                            break;
                        default:
                            fcqFuncResponse = FcqFuncResponse.error(FcqFuncResponse.Msg.INVALID_PARAM);
                            break;
                    }
                    return fcqFuncResponse;
                }).orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.INVALID_PARAM));
    }

    /**
     * 执行mapper
     * @param param 参数
     * @param <T> 传入的类型
     * @param <R> 返回的结果类型
     * @return 返回的结果
     */
    private <T, R> FcqFuncResponse execMapper(FcqFunctionParam<T, R> param) {
        return Optional.ofNullable(param.getMapper())
                .map(
                        mapper -> Optional.ofNullable(mapper.apply(param.getData()))
                                .map(FcqFuncResponse::success)
                                .orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.EMPTY_MESSAGE)))
                .orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.INVALID_PARAM));
    }

    /**
     * 执行supplier
     * @param param 参数
     * @param <T> 传入的数据类型
     * @param <R> 返回的结果类型
     * @return 返回的结果
     */
    private <T, R> FcqFuncResponse execSupplier(FcqFunctionParam<T, R> param) {
        return Optional.ofNullable(param.getSupplier())
                .map(
                        supplier -> Optional.ofNullable(supplier.get())
                                .map(FcqFuncResponse::success)
                                .orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.EMPTY_MESSAGE)))
                .orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.INVALID_PARAM));
    }

    /**
     * 执行consumer
     * @param param 参数
     * @param <T> 传入的数据类型
     * @param <R> 返回的结果类型
     * @return 返回的结果
     */
    private <T, R> FcqFuncResponse execConsumer(FcqFunctionParam<T, R> param) {
        return Optional.ofNullable(param.getConsumer())
                .map(
                        consumer -> {
                            consumer.accept(param.getData());
                            return FcqFuncResponse.success(FcqFuncResponse.Msg.VOID_RESPONSE);
                        })
                .orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.INVALID_PARAM));
    }

    /**
     * 执行consumer
     * @param param 参数
     * @param <T> 传入的数据类型
     * @param <R> 返回的结果类型
     * @return 返回的结果
     */
    private <T, R> FcqFuncResponse execPerformer(FcqFunctionParam<T, R> param) {
        return Optional.ofNullable(param.getPerformer())
                .map(
                        performer -> {
                            performer.execute();
                            return FcqFuncResponse.success(FcqFuncResponse.Msg.VOID_RESPONSE);
                        })
                .orElseGet(() -> FcqFuncResponse.error(FcqFuncResponse.Msg.INVALID_PARAM));

    }

    public boolean isNeedThread() {
        return needThread;
    }

    public String getFcqQueueName() {
        return fcqQueueName;
    }

    public String getFcqQueueType() {
        return fcqQueueType;
    }

    public void setProcessorContainer(FcqQueueProcessorContainer processorContainer) {
        this.processorContainer = processorContainer;
    }
}
