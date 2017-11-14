package merorin.cloud.cloudnote.fcq.processor;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.log.CommonLogger;

import java.util.*;

/**
 * Description: 通用的队列处理者实现
 *              队列数据结构基于{@link LinkedList}
 *
 * @author guobin On date 2017/11/10.
 * @version 1.0
 * @since jdk 1.8
 */
public class CommonFcqQueueProcessor extends AbstractFcqQueueProcessor {

    private static final CommonLogger LOG = CommonLogger.getLogger(CommonFcqQueueProcessor.class);

    /**
     * 队列
     */
    private final LinkedList<FcqParam> queue = new LinkedList<>();

    /**
     * 队列为空时等待的时长,单位为毫秒
     */
    private final long waitTimeout;

    public CommonFcqQueueProcessor(long waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    @Override
    public FcqProcessResult offer(FcqParam param) {

        synchronized (this.queue) {
            if (this.queue.isEmpty()) {
                this.queue.notifyAll();
                LOG.debug("The queue '{0}' has notified all.",this.getQueueName());
                this.queue.add(param);
            }
            LOG.debug("Offer successfully!Queue:'{0}', queueSize: {1}", this.getQueueName(), this.queue.size());
        }

        return this.fillInSuccessResult(Collections.singletonList(param));
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        //初始化读取的数据列表
        final List<FcqParam> list;
        //用来表示处理wait请求是否出错
        boolean waitFlag;
        synchronized (this.queue) {
            if (waitFlag = this.waitWhenEmpty()) {
                FcqParam data = this.queue.poll();
                list = Optional.ofNullable(data)
                        .map(Collections::singletonList)
                        .orElse(Collections.emptyList());
                LOG.debug("Successful to a poll operation from queue '{0}', current size of queue is {1}, data size is {2}",
                        this.getQueueName(),this.queue.size(),list.size());
            } else {
                list = Collections.emptyList();
            }
        }

        return waitFlag ? this.fillInSuccessResult(list) : FcqProcessResult.error(FcqResultConstant.Message.ERROR);
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        //初始化读取的数据列表
        final List<FcqParam> list = new ArrayList<>(param.getReadCount());
        //用来表示处理wait时是否出错
        boolean waitFlag;
        synchronized (this.queue) {
            if (waitFlag = this.waitWhenEmpty()) {
                int readCount = param.getReadCount();
                Iterator<FcqParam> iterator = this.queue.iterator();
                while (iterator.hasNext() && readCount-- > 0) {
                    Optional.ofNullable(iterator.next()).ifPresent(list::add);
                }
                LOG.debug("Successful to a getFromHead operation from queue '{0}', current size of queue is {1}, data size is {2}",
                        this.getQueueName(),this.queue.size(),list.size());
            }
        }

        return waitFlag ? this.fillInSuccessResult(list) : FcqProcessResult.error(FcqResultConstant.Message.ERROR);
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        List<FcqParam> list;
        synchronized (this.queue) {
            list = new ArrayList<>(this.queue);
            LOG.debug("Successful to a getAllElements operation from queue '{0}', current size of queue is {1}, data size is {2}",
                    this.getQueueName(), this.queue.size(), list.size());
        }
        return this.fillInSuccessResult(list);
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        List<FcqParam> list;
        synchronized (this.queue) {
            list = new ArrayList<>(this.queue.size());
            while (!this.queue.isEmpty()) {
                Optional.ofNullable(this.queue.poll()).ifPresent(list::add);
            }
            LOG.debug("Successful to a pollAllElements operation from queue '{0}', current size of queue is {1}, data size is {2}",
                    this.getQueueName(), this.queue.size(), list.size());
        }
        return this.fillInSuccessResult(list);
    }

    @Override
    public FcqProcessResult putToHead(FcqParam param) {
        List<FcqParam> list;
        synchronized (this.queue) {
            if (this.queue.isEmpty()) {
                this.queue.notifyAll();
                LOG.debug("The queue '{0}' has notified all.",this.getQueueName());
                this.queue.addFirst(param);
            }
            LOG.debug("putToHead successfully!Queue:'{0}', queueSize: {1}", this.getQueueName(), this.queue.size());
        }
        return this.fillInSuccessResult(Collections.singletonList(param));
    }

    /**
     * 当队列为空时进行等待
     * @return 处理结果
     */
    private boolean waitWhenEmpty() {
        boolean result = true;
        if (this.queue.isEmpty()) {
            try {
                //队列为空则进行等待
                this.queue.wait(this.waitTimeout);
                LOG.debug("The empty queue '{0}' is waiting.", this.getQueueName());
            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                result = false;
            }
        }
        return result;
    }
}
