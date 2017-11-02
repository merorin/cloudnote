package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.core.impl.FcqDataExecutorContainer;
import merorin.cloud.cloudnote.fcq.core.impl.FcqQueueProcessorContainer;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Description: fcq主要函数入口
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqMainTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(FcqMainTemplate.class);

    private final FcqDataExecutorContainer executorContainer;

    private final FcqQueueProcessorContainer processorContainer;

    private final FcqConfigContainer configContainer;

    private boolean hasInit = false;

    public FcqMainTemplate(FcqConfigContainer container) {
        this.configContainer = container;
        this.processorContainer = new FcqQueueProcessorContainer(this.configContainer);
        this.executorContainer = new FcqDataExecutorContainer(this.configContainer, this.processorContainer);
    }

    @PreDestroy
    public void destroy() {
        this.configContainer.getThreadPool().shutDown(10000);
    }

    /**
     * 执行初始化方法
     */
    @PostConstruct
    public void init() {
        LOG.info("Begin to initialize fcq......");
        this.executorContainer.run();
        this.hasInit = true;
        LOG.info("Finish to initialize fcq......");
    }

    /**
     * 获取队列处理者
     * @return 队列处理者
     */
    public FcqQueueProcessorContainer getQueueProcessor() {
        if (this.hasInit) {
            return this.processorContainer;
        } else {
            LOG.error("FcqMainTemplate has not been initialized yet.Fail to get the processor container.");
            return null;
        }
    }

    /**
     * 获取数据执行者
     * @return 获取到的数据执行者
     */
    public FcqDataExecutorContainer getDataExecutor() {
        if (this.hasInit) {
            return this.executorContainer;
        } else {
            LOG.error("FcqMainTemplate has not been initialized yet.Fail to get the executor container.");
            return null;
        }
    }
}
