package merorin.cloud.cloudnote.fcq.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import merorin.cloud.cloudnote.fcq.core.impl.FcqDataExecutorContainer;
import merorin.cloud.cloudnote.fcq.core.impl.FcqQueueProcessorContainer;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.fcq.util.FcqConfigContainer;
import merorin.cloud.cloudnote.fcq.util.FcqFuncPerformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description: fcq主要函数入口
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqMainTemplate<E> {

    private static final Logger LOG = LoggerFactory.getLogger(FcqMainTemplate.class);

    private static final int DEFAULT_READ_COUNT = 1;

    private static final int FIRST_INDEX = 0;

    private final FcqConfigContainer configContainer;

    private final FcqDataExecutorContainer executorContainer;

    private final FcqQueueProcessorContainer processorContainer;

    /**
     * 是否已经初始化
     */
    private boolean hasInit = false;

    public FcqMainTemplate(FcqConfigContainer container) {
        this.configContainer = container;
        this.processorContainer = new FcqQueueProcessorContainer(this.configContainer);
        this.executorContainer = new FcqDataExecutorContainer(this.configContainer);
    }

    @PreDestroy
    public void destroy() {
        this.configContainer.getThreadPool().shutDown(10000);
    }

    /**
     * 执行初始化方法
     */
    public void init() {
        LOG.info("Begin to initialize fcq......");
        this.executorContainer.run();
        this.hasInit = true;
        LOG.info("Finish to initialize fcq......");
    }

    /**
     * 提交一个新的执行者
     * @param templateKey 模板类的key
     * @param fcqQueueName 执行者队列名
     */
    public void submitNewExecutor(String templateKey, String fcqQueueName) {
        this.configContainer.addNewExecutor(templateKey, fcqQueueName);
    }

    /**
     * 向队列中添加数据,{@code value} 不能为空
     * @param value 数据
     * @param fcqTypeName 队列类型
     * @param fcqQueueName 队列名
     * @throws NullPointerException 如果{@code value}为空
     */
    public void offer(E value, String fcqTypeName, String fcqQueueName) {
        this.offer(value, null, fcqTypeName, fcqQueueName);
    }

    /**
     * 向队列中添加数据,{@code value} 不能为空
     * @param value 数据
     * @param key 这条数据所对应的dataKey
     * @param fcqTypeName 队列类型
     * @param fcqQueueName 队列名
     * @throws NullPointerException 如果{@code value}为空
     */
    public void offer(E value, String key, String fcqTypeName, String fcqQueueName) {
        if (this.hasInit) {
            Optional.of(value).ifPresent(item -> {
                Instant now = Instant.now();

                String dataKey = Optional.ofNullable(key).orElse(this.buildKey(now, item.hashCode()));
                FcqParam param = new FcqParam();
                param.setTimestamp(now);
                param.setDataKey(dataKey);
                param.setFcqTypeName(fcqTypeName);
                param.setFcqQueueName(fcqQueueName);
                param.setData(JSON.toJSONString(item));

                this.logIfError(this.processorContainer.offer(param), "common-offer");
            });
        } else {
            LOG.error("The fcq hasn't been initialized yet.");
        }
    }

    /**
     * 向队列中添加函数,{@code value} 不能为空
     * @param performer 待执行的方法
     * @param fcqTypeName 队列类型
     * @param fcqQueueName 队列名
     * @throws NullPointerException 如果{@code performer}为空
     */
    public void offer(FcqFuncPerformer performer, String fcqTypeName, String fcqQueueName) {
        this.offer(performer, null, fcqTypeName, fcqQueueName);
    }

    /**
     * 向队列中添加函数,{@code value} 不能为空
     * @param performer 待执行的方法
     * @param key 这条数据所对应的dataKey
     * @param fcqTypeName 队列类型
     * @param fcqQueueName 队列名
     * @throws NullPointerException 如果{@code performer}为空
     */
    public void offer(FcqFuncPerformer performer, String key, String fcqTypeName, String fcqQueueName) {
        if (this.hasInit) {
            Optional.of(performer).ifPresent(item -> {
                Instant now = Instant.now();

                String dataKey = Optional.ofNullable(key).orElse(this.buildKey(now, item.hashCode()));
                FcqParam param = new FcqParam();
                param.setTimestamp(now);
                param.setDataKey(dataKey);
                param.setPerformer(item);
                param.setFcqTypeName(fcqTypeName);
                param.setFcqQueueName(fcqQueueName);

                this.logIfError(this.processorContainer.offer(param), "func-offer");
            });
        } else {
            LOG.error("The fcq hasn't been initialized yet.");
        }
    }

    /**
     * 从队列中弹出一条数据
     * @param fcqQueueName 操作的目标队列
     * @param fcqTypeName 队列的类型名
     * @return 弹出的数据集合
     */
    public List<E> poll(String fcqQueueName, String fcqTypeName) {
        return this.poll(fcqQueueName, fcqTypeName, DEFAULT_READ_COUNT);
    }

    /**
     * 从队列中弹出多条数据
     * @param queueName 队列名
     * @param fcqTypeName 队列的类型名
     * @param readCount 读取的数据数目
     * @return 返回结果
     */
    public List<E> poll(String queueName, String fcqTypeName, int readCount) {
        final List<E> list;
        if (this.hasInit) {
            FcqParam param = new FcqParam();
            param.setTimestamp(Instant.now());
            param.setFcqTypeName(fcqTypeName);
            param.setFcqQueueName(queueName);
            param.setReadCount(readCount);
            list = this.convertValues(this.processorContainer.poll(param), "poll");
        } else {
            list = null;
            LOG.error("The fcq hasn't been initialized yet.");
        }

        return list;
    }

    /**
     * 从队列头读取一条数据
     * @param fcqQueueName 目标队列名
     * @param fcqTypeName 目标队列的类型
     * @return 获取的返回数据集合
     */
    public List<E> getFromHead(String fcqQueueName, String fcqTypeName) {
        return this.getFromHead(fcqQueueName, fcqTypeName, DEFAULT_READ_COUNT);
    }

    /**
     * 对队列头开始读取数条数据
     * @param fcqQueueName 目标队列名
     * @param fcqTypeName 目标队列的类型
     * @param readCount 读取的次数
     * @return 获取的返回数据集合
     */
    public List<E> getFromHead(String fcqQueueName, String fcqTypeName, int readCount) {
        List<E> list;

        if (this.hasInit) {
            FcqParam param = new FcqParam();
            param.setTimestamp(Instant.now());
            param.setFcqTypeName(fcqTypeName);
            param.setFcqQueueName(fcqQueueName);
            param.setReadCount(readCount);
            list = this.convertValues(this.processorContainer.getFromHead(param), "getFromHead");
        } else {
            list = null;
            LOG.error("The fcq hasn't been initialized yet.");
        }

        return list;
    }

    /**
     * 在队列中查找元素
     * @param fcqQueueName 队列名
     * @param key 键
     * @param fcqTypeName 队列类型名
     * @return 获取的数据
     */
    public E findValue(String fcqQueueName, String key, String fcqTypeName) {
        E value;
        if (this.hasInit) {
            FcqParam param = new FcqParam();
            param.setTimestamp(Instant.now());
            param.setFcqTypeName(fcqTypeName);
            param.setFcqQueueName(fcqQueueName);
            param.setDataKey(key);

            value = Optional.of(this.convertValues(this.processorContainer.findValue(param), "findValue"))
                    .map(list -> list.get(FIRST_INDEX))
                    .orElse(null);
        } else {
            value = null;
            LOG.error("The fcq hasn't been initialized yet.");
        }

        return value;
    }

    /**
     * 根据数据的key来删除一条数据
     * @param fcqQueueName 队列名
     * @param fcqTypeName 队列类型
     * @param key 键
     * @return 获取的数据
     */
    public E removeValue(String fcqQueueName, String fcqTypeName, String key) {
        E value;
        if (this.hasInit) {
            FcqParam param = new FcqParam();
            param.setTimestamp(Instant.now());
            param.setFcqTypeName(fcqTypeName);
            param.setFcqQueueName(fcqQueueName);
            param.setDataKey(key);

            value = Optional.of(this.convertValues(this.processorContainer.findValue(param), "removeValue"))
                    .map(list -> list.get(FIRST_INDEX))
                    .orElse(null);
        } else {
            value = null;
            LOG.error("The fcq hasn't been initialized yet.");
        }

        return value;
    }

    /**
     * 读取队列中所有的数据但不弹出
     * @param fcqQueueName 目标队列名
     * @param fcqTypeName 目标队列类型名
     * @return 返回的数据集合
     */
    public List<E> getAll(String fcqQueueName, String fcqTypeName) {
        List<E> list;

        if (this.hasInit) {
            FcqParam param = new FcqParam();
            param.setTimestamp(Instant.now());
            param.setFcqTypeName(fcqTypeName);
            param.setFcqQueueName(fcqQueueName);

            list = this.convertValues(this.processorContainer.getAllElements(param), "getAll");
        } else {
            list = null;
            LOG.error("The fcq hasn't been initialized yet.");
        }

        return list;
    }

    /**
     * 弹出队列中的所有数据
     * @param fcqQueueName 目标队列名
     * @param fcqTypeName 目标队列类型名
     * @return 返回的数据集合
     */
    public List<E> pollAll(String fcqQueueName, String fcqTypeName) {
        List<E> list;

        if (this.hasInit) {
            FcqParam param = new FcqParam();
            param.setTimestamp(Instant.now());
            param.setFcqTypeName(fcqTypeName);
            param.setFcqQueueName(fcqQueueName);

            list = this.convertValues(this.processorContainer.pollAllElements(param), "pollAll");
        } else {
            list = null;
            LOG.error("The fcq hasn't been initialized yet.");
        }

        return list;
    }


    /**
     * 执行读者的读取方法
     */
    public void read(String queueName) {
        if (this.hasInit) {
            this.executorContainer.read(queueName);
        } else {
            LOG.error("The fcq hasn't been initialized yet.");
        }
    }

    /**
     * 构造一个数据的key
     * @param time 时间戳
     * @param hashCode 数据的hash值
     * @return 构造出来的key
     */
    private String buildKey(Instant time, int hashCode) {
        return Optional.ofNullable(time)
                .map(timestamp -> timestamp + "-" + hashCode)
                .orElse(null);
    }

    /**
     * 记录错误日志信息
     * @param result 执行返回的结果
     * @param methodName 调用的方法名
     */
    private void logIfError(FcqProcessResult result, String methodName) {
        Optional.of(result).filter(res -> !res.isSuccess())
                .ifPresent(res -> LOG.error("Fail to execute function:{} due to exception, {}", methodName, result.getMessage()));
    }

    /**
     * 将结果转化成list元素集合
     * @param result 执行结果
     * @return 转化结果
     */
    private List<E> convertValues(FcqProcessResult result, String methodName) {
        this.logIfError(result, methodName);
        return Optional.of(result)
                .filter(FcqProcessResult::isSuccess)
                .map(FcqProcessResult::getValues)
                .map(Collection::stream)
                .map(stream -> stream.map(value -> value.getObj(new TypeReference<E>(){}))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
