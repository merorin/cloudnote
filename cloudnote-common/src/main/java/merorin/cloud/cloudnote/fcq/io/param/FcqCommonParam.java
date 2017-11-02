package merorin.cloud.cloudnote.fcq.io.param;

import java.util.Date;

/**
 * Description: fcq框架的基础数据结构
 *
 * @param <T> 该参数封装的核心数据类型
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqCommonParam<T> {

    /**
     * fcq队列的类型名
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
    private Date timestamp;

    /**
     * 需要读取的数据总数
     */
    private int readCount;

    /**
     * 封装的核心数据
     */
    private T data;

    /**
     * token,在fcq内部生成并用来校验
     */
    private byte[] token;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }
}
