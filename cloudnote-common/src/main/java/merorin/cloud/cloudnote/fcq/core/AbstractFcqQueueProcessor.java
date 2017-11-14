package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

import java.util.List;
import java.util.Optional;

/**
 * Description: fcq队列处理者的抽象类,所有fcq的处理者都必须实现这个类
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public abstract class AbstractFcqQueueProcessor implements FcqQueueProcessable {

    /**
     * 队列名
     */
    private String queueName;

    /**
     * 提交一份数据到队列中
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult offer(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 从队列中弹出一份数据
     * @param param 封装的fcq数据
     * @return 处理结果
     */
    @Override
    public FcqProcessResult poll(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 从队列首开始获取n条数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 获取一条特定的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult findValue(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 删除一条特定的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult removeValue(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 获取队列中所有的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 弹出队列中所有的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 将元素放到队列首位
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult putToHead(FcqParam param) {
        return FcqProcessResult.error("该方法在对应的队列处理者中中尚未实现.");
    }

    /**
     * 填充一个成功的返回结果
     * @param list 数据集合
     */
    protected FcqProcessResult fillInSuccessResult(final List<FcqParam> list) {
        String message = list.isEmpty() ? FcqResultConstant.Message.NOT_FOUND : FcqResultConstant.Message.SUCCESS;
        final FcqProcessResult result = FcqProcessResult.success(message);
        result.setValues(list);
        result.setCount(list.size());
        result.setTotalCount(list.size());
        return result;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = Optional.ofNullable(this.queueName).orElse(queueName);
    }
}
