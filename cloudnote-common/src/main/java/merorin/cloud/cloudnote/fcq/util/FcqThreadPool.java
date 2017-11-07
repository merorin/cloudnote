package merorin.cloud.cloudnote.fcq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: fcq的线程池
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqThreadPool {

    private static final Logger LOG = LoggerFactory.getLogger(FcqThreadPool.class);

    private static final String THREAD_FACTORY_NAME = "FcqThread";

    /**
     * 线程池执行者
     */
    private final ThreadPoolExecutor threadPoolExecutor;

    /**
     * 构造器
     * @param corePoolSize 核心线程池可保留的线程数目,即便它们处于闲置状态,除非{@code allowCoreThreadTimeOut}被设置
     * @param maximumPoolSize 线程池中允许存放的最大线程数目
     * @param keepAliveTime 当线程数目较核心线程池中要多时
     *                      额外的空闲线程能够等待新任务的最大时间
     *                      如果迟迟没有新任务,在超出时间后这些闲置线程将被终止
     */
    FcqThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.threadPoolExecutor =
                new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(),
                        new FcqThreadFactory(THREAD_FACTORY_NAME));
    }

    /**
     * 提交一条线程
     * @param r {@code Runnable}接口
     */
    public void submit(Runnable r) {
        this.threadPoolExecutor.execute(r);
        LOG.info("Fcq has submit a new thread to thread pool......current pool size is {}, max allowed size is {}",
                this.threadPoolExecutor.getPoolSize(), this.threadPoolExecutor.getMaximumPoolSize());
    }

    /**
     * 立刻停止所有的线程
     * @param timeout 等待线程池终止的时间
     */
    public void shutDown(long timeout) {
        this.threadPoolExecutor.shutdown();
        try {
            if (!this.threadPoolExecutor.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                this.threadPoolExecutor.shutdownNow();
                if (!this.threadPoolExecutor.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                    LOG.error("The fcq thread pool did not terminate...");
                }
            }
        } catch (Exception ex) {
            LOG.warn("Fail to shutdown the thread pool due to exception : {}",ex);
            this.threadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        LOG.info("Fcq has shutdown the thread pool...");
    }
}
