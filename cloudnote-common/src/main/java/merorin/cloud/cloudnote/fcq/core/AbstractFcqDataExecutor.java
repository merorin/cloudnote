package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.util.FcqFuncPerformer;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

import java.util.Optional;

/**
 * Description: fcq队列读者的抽象类,所有读者都必须继承这个类
 *
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public abstract class AbstractFcqDataExecutor implements FcqDataExecutable {

    /**
     * 队列名字
     */
    private String fcqQueueName;

    /**
     * 队列类型
     */
    private final String fcqQueueType;

    /**
     * 用来判断是否要启动一个后台线程
     */
    private final boolean needThread;

    /**
     * 如果有线程,是否已经启动
     */
    private boolean threadActivated = false;

    public AbstractFcqDataExecutor(String fcqQueueType, boolean needThread) {
        this.fcqQueueType = fcqQueueType;
        this.needThread = needThread;
    }

    public AbstractFcqDataExecutor(String fcqQueueName, String fcqQueueType, boolean needThread) {
        this.fcqQueueName = fcqQueueName;
        this.fcqQueueType = fcqQueueType;
        this.needThread = needThread;
    }

    /**
     * 由外界发起的读取请求,读取封装{@code FunctionalInterface}的fcq数据
     * @return 处理结果
     */
    @Override
    public FcqProcessResult read() {
        return FcqProcessResult.error("该方法在对应的数据执行者中尚未实现.");
    }

    /**
     * 此方法被调用时会启动一条守护线程来执行该方法
     * 所有需要启动守护线程的执行者都必须实现这个方法
     */
    @Override
    public void run() {
        //默认不实现任何方法
    }

    /**
     * 调用传入的function
     * @param param 传入的参数,不能为空
     */
    protected void execFunction(final FcqParam param) {
        Optional.ofNullable(param).map(FcqParam::getPerformer)
                .ifPresent(FcqFuncPerformer::execute);
    }


    public boolean isNeedThread() {
        return needThread;
    }

    public void setFcqQueueName(String fcqQueueName) {
        this.fcqQueueName = Optional.ofNullable(this.fcqQueueName).orElse(fcqQueueName);
    }

    public String getFcqQueueName() {
        return fcqQueueName;
    }

    public String getFcqQueueType() {
        return fcqQueueType;
    }

    public boolean isThreadActivated() {
        return threadActivated;
    }

    public void setThreadActivated(boolean threadActivated) {
        this.threadActivated = threadActivated;
    }
}
