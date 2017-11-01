package merorin.cloud.cloudnote.fcq.util;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.core.impl.ErrorFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.core.impl.ErrorFcqQueueProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Description: Fcq配置数据的主要容器
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqConfigContainer {

    private static final Logger LOG = LoggerFactory.getLogger(FcqConfigContainer.class);

    /**
     * 用以存储执行者的map
     */
    private final Map<String, AbstractFcqDataExecutor> executorMap;

    /**
     * 用以存储处理者的map
     */
    private final Map<String, AbstractFcqQueueProcessor> processorMap;

    /**
     * 默认的错误执行者
     */
    private final AbstractFcqDataExecutor errorExecutor;

    /**
     * 默认的错误处理者
     */
    private final AbstractFcqQueueProcessor errorProcessor;

    /**
     * 线程池
     */
    private final FcqThreadPool threadPool;

    public FcqConfigContainer(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              Map<String, AbstractFcqDataExecutor> executorMap,
                              Map<String, AbstractFcqQueueProcessor> processorMap) {
        this.executorMap = executorMap;
        this.processorMap = processorMap;
        this.threadPool = new FcqThreadPool(corePoolSize, maximumPoolSize, keepAliveTime);
        this.errorExecutor = new ErrorFcqDataExecutor();
        this.errorProcessor = new ErrorFcqQueueProcessor();
    }

    /**
     * 获取队列处理者
     * @param fcqQueueName 队列名
     * @param fcqQueueType 队列类型
     * @return 得到的处理者
     */
    public AbstractFcqQueueProcessor getProcessor(final String fcqQueueName, final String fcqQueueType) {

        return Optional.ofNullable(fcqQueueName)
                .filter(this.processorMap::containsKey)
                .map(name -> Optional.ofNullable(this.processorMap.get(name))
                        .orElse(this.errorProcessor))
                .orElseGet(() -> Optional.ofNullable(this.generateProcessor(fcqQueueType))
                        .map((processor) -> {
                            processor.setQueueName(fcqQueueName);
                            synchronized (this.processorMap) {
                                this.processorMap.put(fcqQueueName, processor);
                            }
                            return processor;
                        }).orElse(this.errorProcessor));
    }

    /**
     * 获取数据执行者
     * @param fcqQueueName 队列名
     * @return 得到的执行者
     */
    public AbstractFcqDataExecutor getExecutor(final String fcqQueueName) {

        return Optional.ofNullable(fcqQueueName)
                .filter(this.executorMap::containsKey)
                .map(name -> Optional.ofNullable(this.executorMap.get(name))
                        .orElse(this.errorExecutor))
                .orElse(this.errorExecutor);
    }

    /**
     * 获取所有的数据执行者
     * @return 返回的数据执行者列表
     */
    public List<AbstractFcqDataExecutor> getAllExecutors() {
        return new ArrayList<>(this.executorMap.values());
    }

    /**
     * 生成一个fcq处理者
     * @param className 类名
     * @return 生成的队列处理者
     */
    private AbstractFcqQueueProcessor generateProcessor(String className) {

        return Optional.ofNullable(className).map(this::doGenerateProcessor).orElseGet(() -> {
            LOG.error("Invalid fcqTypeName for null!Fail to get the instance...");
            return null;
        });
    }

    /**
     * 实际获取一个处理者bean的执行方法
     * @param className 类名
     * @return 生成的队列处理者
     */
    private AbstractFcqQueueProcessor doGenerateProcessor(String className) {
        AbstractFcqQueueProcessor newProcessor;

        try {
            newProcessor = FcqBeanUtils.getBean(className);
            LOG.info("Creating a new processor bean for {}......", className);
        } catch (Exception ex) {
            newProcessor = null;
            LOG.error("Fail to get an processor bean with class:{}, the exception is {}",className, ex);
        }

        return newProcessor;
    }

    public FcqThreadPool getThreadPool() {
        return threadPool;
    }
}
