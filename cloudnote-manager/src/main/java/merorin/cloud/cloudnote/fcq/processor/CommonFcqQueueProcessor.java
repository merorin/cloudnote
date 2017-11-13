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
        final FcqProcessResult result = new FcqProcessResult();

        synchronized (this.queue) {
            if (this.queue.isEmpty()) {
                this.queue.notifyAll();
                LOG.debug("The queue '{0}' has notified all.",this.getQueueName());
                this.queue.add(param);
            }
            LOG.debug("Offer successfully!Queue:'{0}', queueSize: {1}", this.getQueueName(), this.queue.size());
        }

        this.fillInSuccessResult(result, Collections.singletonList(param));

        return result;
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        final FcqProcessResult result = new FcqProcessResult();

        //初始化读取的数据列表
        final List<FcqParam> list = new ArrayList<>(param.getReadCount());
        //用来表示处理wait请求是否出错
        boolean waitFlag;
        synchronized (this.queue) {
            if (waitFlag = this.waitWhenEmpty()) {
                int readCount = Math.min(param.getReadCount(), this.queue.size());
                while (readCount-- > 0) {
                    Optional.ofNullable(this.queue.poll()).ifPresent(list::add);
                }
                LOG.debug("Successful to a poll operation from queue '{0}, current size of queue is {1}, data size is {2}'",
                        this.getQueueName(),this.queue.size(),list.size());
            }
        }
        if (waitFlag) {
            this.fillInSuccessResult(result, list);
        }

        return result;
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        final FcqProcessResult result = new FcqProcessResult();

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
                LOG.debug("Successful to a getFromHead operation from queue '{0}, current size of queue is {1}, data size is {2}'",
                        this.getQueueName(),this.queue.size(),list.size());
            }
        }
        if (waitFlag) {
            this.fillInSuccessResult(result, list);
        }

        return result;
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        final FcqProcessResult result = new FcqProcessResult();
        List<FcqParam> list;
        synchronized (this.queue) {
            list = new ArrayList<>(this.queue);
        }
        this.fillInSuccessResult(result, list);
        return result;
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        final FcqProcessResult result = new FcqProcessResult();
        List<FcqParam> list;
        synchronized (this.queue) {
            list = new ArrayList<>(this.queue.size());
            while (!this.queue.isEmpty()) {
                Optional.ofNullable(this.queue.poll()).ifPresent(list::add);
            }
        }
        this.fillInSuccessResult(result, list);
        return super.pollAllElements(param);
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

    /**
     * 填充一个成功的返回结果
     * @param result 待填充对象
     * @param list 数据集合
     */
    private void fillInSuccessResult(final FcqProcessResult result, final List<FcqParam> list) {
        result.setCode(FcqResultConstant.Code.SUCCESS);
        result.setMessage(list.isEmpty() ? FcqResultConstant.Message.NOT_FOUND : FcqResultConstant.Message.SUCCESS);
        result.setValues(list);
        result.setCount(list.size());
        result.setTotalCount(list.size());
    }
}
