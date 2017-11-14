package merorin.cloud.cloudnote.fcq.processor;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.log.CommonLogger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description: 基于redis实现的队列处理者
 *              队列交由redis处理,在使用时需要自己配置redisTemplate
 *              在弹出队列时如果队列中无元素将不会进行等待
 *
 * @author guobin On date 2017/11/10.
 * @version 1.0
 * @since jdk 1.8
 */
public class RedisFcqQueueProcessor extends AbstractFcqQueueProcessor {

    private static final CommonLogger LOG = CommonLogger.getLogger(RedisFcqQueueProcessor.class);

    private static final String FCQ_REDIS_KEY = "fcq_redis_queue";

    /**
     * 如果列表为空的等待超时时长,单位为毫秒
     */
    private final int timeout;

    /**
     * 全局锁
     */
    private final ReentrantLock globalLock = new ReentrantLock();

    @Resource
    private RedisTemplate<String, FcqParam> redisTemplate;

    public RedisFcqQueueProcessor(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public FcqProcessResult offer(FcqParam param) {
        FcqProcessResult result;
        this.globalLock.lock();
        try {
            long count = this.redisTemplate.opsForList().rightPush(FCQ_REDIS_KEY, param);
            result = this.fillInSuccessResult(count > 0 ? Collections.singletonList(param) : Collections.emptyList());
        } catch (Exception ex) {
            result = FcqProcessResult.error(FcqResultConstant.Message.ERROR);
            result.setExceptionMsg(ex.toString());
        } finally {
            this.globalLock.unlock();
        }
        LOG.debug("Successful to a offer operation from queue '{0}'", this.getQueueName());

        return result;
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        FcqProcessResult result;
        this.globalLock.lock();
        try {
            FcqParam data = this.redisTemplate.opsForList().leftPop(FCQ_REDIS_KEY, this.timeout, TimeUnit.MILLISECONDS);
            final List<FcqParam> list = Optional.ofNullable(data)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
            LOG.debug("Successful to a poll operation from queue '{0}'.", this.getQueueName());
            result = this.fillInSuccessResult(list);
        } catch (Exception ex) {
            result = FcqProcessResult.error(FcqResultConstant.Message.ERROR);
            result.setExceptionMsg(ex.toString());
        } finally {
            this.globalLock.unlock();
        }
        return result;
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        FcqProcessResult result;
        this.globalLock.lock();
        try {
            int size = this.redisTemplate.opsForList().size(FCQ_REDIS_KEY).intValue();
            int readCount = Math.min(size, param.getReadCount());
            final List<FcqParam> list = new ArrayList<>(readCount);
            int count = 0;
            while (count < readCount) {
                FcqParam data = this.redisTemplate.opsForList().index(FCQ_REDIS_KEY, count++);
                Optional.ofNullable(data).ifPresent(list::add);
            }
            LOG.debug("Successful to a getFromHead operation from queue '{0}', current size of queue is {1}, data size is {2}",
                    this.getQueueName(), size, list.size());
            result = this.fillInSuccessResult(list);
        } catch (Exception ex) {
            result = FcqProcessResult.error(FcqResultConstant.Message.ERROR);
            result.setExceptionMsg(ex.toString());
        } finally {
            this.globalLock.unlock();
        }
        return result;
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        FcqProcessResult result;
        this.globalLock.unlock();
        try {
            int size = this.redisTemplate.opsForList().size(FCQ_REDIS_KEY).intValue();
            final List<FcqParam> list = this.redisTemplate.opsForList().range(FCQ_REDIS_KEY, 0 , size - 1);
            LOG.debug("Successful to a getAllElements operation from queue '{0}', current size of queue is {1}, data size is {2}",
                    this.getQueueName(), size, list.size());
            result = this.fillInSuccessResult(list);
        } catch (Exception ex) {
            result = FcqProcessResult.error(FcqResultConstant.Message.ERROR);
            result.setExceptionMsg(ex.toString());
        } finally {
            this.globalLock.unlock();
        }
        return result;
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        FcqProcessResult result;
        this.globalLock.unlock();
        try {
            int size = this.redisTemplate.opsForList().size(FCQ_REDIS_KEY).intValue();
            final List<FcqParam> list = new ArrayList<>(size);
            while (size-- > 0) {
                FcqParam data = this.redisTemplate.opsForList().leftPop(FCQ_REDIS_KEY);
                Optional.ofNullable(data).ifPresent(list::add);
            }
            LOG.debug("Successful to a pollAllElements operation from queue '{0}', current size of queue is {1}, data size is {2}",
                    this.getQueueName(), size, list.size());
            result = this.fillInSuccessResult(list);
        } catch (Exception ex) {
            result = FcqProcessResult.error(FcqResultConstant.Message.ERROR);
            result.setExceptionMsg(ex.toString());
        } finally {
            this.globalLock.unlock();
        }
        return result;
    }

    @Override
    public FcqProcessResult putToHead(FcqParam param) {
        FcqProcessResult result;
        this.globalLock.unlock();
        try {
            long count = this.redisTemplate.opsForList().leftPush(FCQ_REDIS_KEY, param);
            result = this.fillInSuccessResult(count > 0 ? Collections.singletonList(param) : Collections.emptyList());
        } catch (Exception ex) {
            result = FcqProcessResult.error(FcqResultConstant.Message.ERROR);
            result.setExceptionMsg(ex.toString());
        } finally {
            this.globalLock.unlock();
        }
        return result;
    }
}
