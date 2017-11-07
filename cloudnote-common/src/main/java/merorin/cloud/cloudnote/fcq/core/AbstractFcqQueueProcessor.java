package merorin.cloud.cloudnote.fcq.core;

import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;
import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;

import javax.swing.text.html.Option;
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
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    /**
     * 从队列中弹出一份数据
     * @param param 封装的fcq数据
     * @return 处理结果
     */
    @Override
    public FcqProcessResult poll(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    @Override
    public FcqProcessResult getFromHead(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    /**
     * 获取一条特定的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult findValue(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    /**
     * 删除一条特定的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult removeValue(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    /**
     * 获取队列中所有的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult getAllElements(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    /**
     * 弹出队列中所有的数据
     * @param param 封装的数据
     * @return 处理成功或者失败
     */
    @Override
    public FcqProcessResult pollAllElements(FcqParam param) {
        FcqProcessResult result = new FcqProcessResult();

        result.setCode(FcqResultConstant.Code.ERROR);
        result.setMessage("该方法在对应的队列处理者中中尚未实现.");

        return result;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = Optional.ofNullable(this.queueName).orElse(queueName);
    }
}
