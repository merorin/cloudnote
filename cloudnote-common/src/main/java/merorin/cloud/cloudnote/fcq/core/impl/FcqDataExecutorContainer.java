package merorin.cloud.cloudnote.fcq.core.impl;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.core.FcqDataExecutable;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;

import java.util.List;

/**
 * Description: fcq数据执行者的容器
 *
 * @author guobin On date 2017/11/2.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqDataExecutorContainer implements FcqDataExecutable {

    private final FcqConfigContainer configContainer;

    private final FcqQueueProcessorContainer processorContainer;

    public FcqDataExecutorContainer(FcqConfigContainer configContainer,
                                    FcqQueueProcessorContainer processorContainer) {
        this.configContainer = configContainer;
        this.processorContainer = processorContainer;
    }

    @Override
    public <T> FcqProcessResult<T> read() {
        return null;
    }

    @Override
    public void run() {
        //获取所有的执行者
        final List<AbstractFcqDataExecutor> executors = this.configContainer.getAllExecutors();
        //过滤无需线程的数据执行者,为需要线程的数据执行者提交线程
        executors.stream().filter(AbstractFcqDataExecutor::isNeedThread)
                .forEach(this::setUpAndCommitThread);
    }

    /**
     * 设置并提交一条线程至线程池
     * @param executor 需要创建线程的执行者
     */
    private void setUpAndCommitThread(AbstractFcqDataExecutor executor) {
        executor.setProcessorContainer(this.processorContainer);
        this.configContainer.getThreadPool().submit(executor::run);
    }
}
