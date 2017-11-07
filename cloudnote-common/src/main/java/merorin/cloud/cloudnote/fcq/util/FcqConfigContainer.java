package merorin.cloud.cloudnote.fcq.util;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.core.impl.ErrorFcqDataExecutor;
import merorin.cloud.cloudnote.fcq.core.impl.ErrorFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.io.exception.FcqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description: Fcq配置数据的主要容器,必须定义成bean使用
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqConfigContainer {

    private static final Logger LOG = LoggerFactory.getLogger(FcqConfigContainer.class);

    private static final String BEAN_SCOPE_PROTOTYPE = "prototype";

    private static final String NULL_SPRING_CONFIG = "nullSpringConfig";

    @Resource
    private ConfigurableApplicationContext applicationContext;

    /**
     * 用以存储执行者的map
     */
    private final Map<String, AbstractFcqDataExecutor> executorMap;

    /**
     * 用以存储处理者的map
     */
    private final Map<String, AbstractFcqQueueProcessor> processorMap;

    /**
     * 处理者模板Map
     */
    private final Map<String, AbstractFcqQueueProcessor> processorTempMap;

    /**
     * 执行者模板Map
     */
    private final Map<String, AbstractFcqDataExecutor> executorTempMap;

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
                              Map<String, AbstractFcqQueueProcessor> processorTemplateMap) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, executorMap, processorTemplateMap, null);
    }

    public FcqConfigContainer(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              Map<String, AbstractFcqDataExecutor> executorMap,
                              Map<String, AbstractFcqQueueProcessor> processorTemplateMap,
                              Map<String, AbstractFcqDataExecutor> executorTempMap) {
        this.processorTempMap = this.checkProcessorTempMap(processorTemplateMap);
        this.executorTempMap = this.checkExecutorTempMap(executorTempMap);
        this.executorMap = executorMap;
        this.processorMap = new HashMap<>();
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

        Optional.ofNullable(fcqQueueName).ifPresent((key) -> {
            if (!this.processorMap.containsKey(key)) {
                Optional.ofNullable(this.generateProcessor(fcqQueueType))
                        .ifPresent((processor) -> {
                            processor.setQueueName(fcqQueueName);
                            synchronized (this.processorMap) {
                                this.processorMap.putIfAbsent(fcqQueueName, processor);
                            }
                        });
            }
        });

        return Optional.ofNullable(this.processorMap.get(fcqQueueName)).orElse(this.errorProcessor);
    }

    /**
     * 获取数据执行者
     * @param fcqQueueName 队列名
     * @return 得到的执行者
     */
    public AbstractFcqDataExecutor getExecutor(final String fcqQueueName) {
        return Optional.ofNullable(fcqQueueName).map(this.executorMap::get).orElse(this.errorExecutor);
    }

    /**
     * 获取所有的数据执行者
     * @return 返回的数据执行者列表
     */
    public List<AbstractFcqDataExecutor> getAllExecutors() {
        return new ArrayList<>(this.executorMap.values());
    }

    /**
     * 动态添加一个新的执行者
     * @param tempKey 模板的key
     * @param fcqQueueName fcq队列名字
     */
    public void addNewExecutor(String tempKey, String fcqQueueName) {
        Optional.ofNullable(this.generateExecutor(tempKey)).ifPresent(executor -> {
            executor.setFcqQueueName(fcqQueueName);
            synchronized (this.executorMap) {
                this.executorMap.putIfAbsent(fcqQueueName, executor);
            }
        });
    }

    /**
     * 生成一个fcq处理者
     * @param tempKey processor的模板key
     * @return 生成的队列处理者
     */
    private AbstractFcqQueueProcessor generateProcessor(String tempKey) {
        return Optional.ofNullable(tempKey).map(this::doGenerateProcessor).orElse(null);
    }

    /**
     * 生成一个fcq执行者
     * @param tempKey executor的模板key
     * @return 生成的数据执行者
     */
    private AbstractFcqDataExecutor generateExecutor(String tempKey) {
        return Optional.ofNullable(tempKey).map(this::doGenerateExecutor).orElse(null);
    }

    /**
     * 实际获取一个处理者bean的执行方法
     * @param templateKey 模板key
     * @return 生成的队列处理者
     */
    private AbstractFcqQueueProcessor doGenerateProcessor(String templateKey) {

        return Optional.ofNullable(this.processorTempMap.get(templateKey)).map(processor -> {
            LOG.info("Creating a new processor bean for {}......", templateKey);
            return processor;
        }).orElseGet(() -> {
            LOG.error("Fail to get an processor bean with class:{}",templateKey);
            return null;
        });
    }

    /**
     * 生成一个执行者bean
     * @param templateKey 模板key
     * @return 生成的执行者
     */
    private AbstractFcqDataExecutor doGenerateExecutor(String templateKey) {

        return Optional.ofNullable(this.executorTempMap.get(templateKey)).map((executor) -> {
            LOG.info("Creating a new executor bean for {}......", templateKey);
            return executor;
        }).orElseGet(() -> {
            throw new FcqException("Fail to create a new executor bean for" + templateKey + ".....");
        });
    }

    /**
     * 对传入的templateProcessor进行校验,检查其bean的{@code scope}是否为"prototype"
     * @param processorTemplateMap xml中配置的模板处理者map,不可能为empty
     * @return 筛选过后的结果
     */
    private Map<String, AbstractFcqQueueProcessor> checkProcessorTempMap(Map<String, AbstractFcqQueueProcessor> processorTemplateMap) {

        final Map<String, AbstractFcqQueueProcessor> map = processorTemplateMap.entrySet().stream()
                .map(Map.Entry::getKey).filter(this::validateTemp)
                .collect(Collectors.toMap(key -> key, processorTemplateMap::get));

        if (map.isEmpty()) {
            throw new FcqException("No processor defined!Failed to init the fcq config container.");
        }

        return map;
    }

    /**
     * 对传入的templateExecutor进行校验,检查其bean的{@code scope}是否为"prototype"
     * @param executorTemplateMap xml中配置的模板数据执行者map,可以为empty
     * @return 筛选过后的结果
     */
    private Map<String, AbstractFcqDataExecutor> checkExecutorTempMap(Map<String, AbstractFcqDataExecutor> executorTemplateMap) {

        return Optional.ofNullable(executorTemplateMap).map(map -> map.entrySet().stream()
                .map(Map.Entry::getKey).filter(this::validateTemp)
                .collect(Collectors.toMap(key -> key, executorTemplateMap::get)))
                .orElse(new HashMap<>(0));
    }

    /**
     * 验证一个模板执行者bean的合法性
     * @param templateKey 传入的entry
     * @return 校验结果
     */
    private boolean validateTemp(String templateKey) {
        boolean checkRes;
        String scope = Optional.ofNullable(this.applicationContext)
                .map(ConfigurableApplicationContext::getBeanFactory)
                .map(factory -> factory.getBeanDefinition(templateKey))
                .map(BeanDefinition::getScope)
                .orElse(NULL_SPRING_CONFIG);
        if (!(checkRes = BEAN_SCOPE_PROTOTYPE.equals(scope))) {
            LOG.error("Invalid template named {}! Scope required is 'prototype' but found {}.", templateKey, scope);
        }
        return checkRes;
    }

    public FcqThreadPool getThreadPool() {
        return threadPool;
    }
}
