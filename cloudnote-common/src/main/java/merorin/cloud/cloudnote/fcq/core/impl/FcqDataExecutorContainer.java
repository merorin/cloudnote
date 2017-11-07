package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.core.FcqDataExecutable;
import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;

import java.util.List;
import java.util.Optional;

/**
 * Description: fcq数据执行者的容器
 *
 * @author guobin On date 2017/11/2.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqDataExecutorContainer implements FcqDataExecutable {

    private final FcqConfigContainer configContainer;

    public FcqDataExecutorContainer(FcqConfigContainer configContainer) {
        this.configContainer = configContainer;
    }

    @Override
    public FcqProcessResult read() {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在FcqDataExecutorContainer中尚未实现.");

        return result;
    }

    /**
     * 外界读取执行
     * @param fcqQueueName 队列名字
     */
    public void read(String fcqQueueName) {
        Optional.of(fcqQueueName)
                .map(this.configContainer::getExecutor)
                .filter(this::isErrorExecutor)
                .ifPresent(AbstractFcqDataExecutor::read);
    }

    @Override
    public void run() {
        //获取所有的执行者
        final List<AbstractFcqDataExecutor> executors = this.configContainer.getAllExecutors();
        //过滤无需线程的数据执行者,为需要线程的数据执行者提交线程
        executors.forEach(this::setUpAndCommitThread);
    }

    /**
     * 判断是否是错误的数据执行者
     * @param executor 传入的参数
     * @return 得到的处理者
     */
    private boolean isErrorExecutor(final AbstractFcqDataExecutor executor) {
        return Optional.ofNullable(executor)
                .map(item -> !(item instanceof ErrorFcqDataExecutor))
                .orElse(false);
    }

    /**
     * 设置并提交一条线程至线程池
     * @param executor 需要创建线程的执行者
     */
    private void setUpAndCommitThread(AbstractFcqDataExecutor executor) {
        Optional.ofNullable(executor).ifPresent(item -> {
            if (item.isNeedThread()) {
                this.configContainer.getThreadPool().submit(executor::run);
            }
        });
    }
}
