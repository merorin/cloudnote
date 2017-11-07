package merorin.cloud.cloudnote.fcq.io.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import merorin.cloud.cloudnote.fcq.util.FcqFuncPerformer;

import java.time.Instant;

/**
 * Description: fcq基础数据
 *
 * @author guobin On date 2017/11/3.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqParam {

    /**
     * fcq队列的类型
     */
    private String fcqTypeName;

    /**
     * fcq队列的队列名
     */
    private String fcqQueueName;

    /**
     * 该数据的标识名
     */
    private String dataKey;

    /**
     * 操作的时间戳
     */
    private Instant timestamp;

    /**
     * 需要读取的数据总数
     */
    private int readCount;

    /**
     * 当这是一条普通的fcq数据时,{@code data}封装的是核心数据
     * 用JSONString形式封装
     */
    private String data;

    /**
     * 传入的待执行函数
     */
    private FcqFuncPerformer performer;

    /**
     * 读取后是否要删除
     */
    private boolean removeAfterRead;

    public String getFcqTypeName() {
        return fcqTypeName;
    }

    public void setFcqTypeName(String fcqTypeName) {
        this.fcqTypeName = fcqTypeName;
    }

    public String getFcqQueueName() {
        return fcqQueueName;
    }

    public void setFcqQueueName(String fcqQueueName) {
        this.fcqQueueName = fcqQueueName;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FcqFuncPerformer getPerformer() {
        return performer;
    }

    public void setPerformer(FcqFuncPerformer performer) {
        this.performer = performer;
    }

    public boolean isRemoveAfterRead() {
        return removeAfterRead;
    }

    public void setRemoveAfterRead(boolean removeAfterRead) {
        this.removeAfterRead = removeAfterRead;
    }

    /**
     * 将{@code data}转化成一个对象
     * @param type 目标的类
     * @param <T> 类的类型
     * @return 转出的对象
     */
    public <T> T getObj(TypeReference<T> type) {
        return JSON.parseObject(this.data, type);
    }
}
