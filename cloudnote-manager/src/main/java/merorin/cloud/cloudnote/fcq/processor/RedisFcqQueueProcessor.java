package merorin.cloud.cloudnote.fcq.processor;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Description: 基于redis实现的队列处理者
 *              队列交由redis处理,在使用时需要自己配置redisTemplate
 *
 * @author guobin On date 2017/11/10.
 * @version 1.0
 * @since jdk 1.8
 */
public class RedisFcqQueueProcessor extends AbstractFcqQueueProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(RedisFcqQueueProcessor.class);

    @Resource
    private RedisTemplate<String, FcqParam> redisTemplate;

    @Override
    public FcqProcessResult offer(FcqParam param) {
        final FcqProcessResult result = new FcqProcessResult();

        return super.offer(param);
    }

    @Override
    public FcqProcessResult poll(FcqParam param) {
        return super.poll(param);
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        return super.getFromHead(param);
    }

    @Override
    public FcqProcessResult findValue(FcqParam param) {
        return super.findValue(param);
    }

    @Override
    public FcqProcessResult removeValue(FcqParam param) {
        return super.removeValue(param);
    }

    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        return super.getAllElements(param);
    }

    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        return super.pollAllElements(param);
    }
}
