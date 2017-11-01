package merorin.cloud.cloudnote.bo.data.redis;

/**
 * Description: 封装redis请求的参数
 *
 * @author guobin On date 2017/10/28.
 * @version 1.0
 * @since jdk 1.8
 */
public class RedisCacheParam<T> {

    /**
     * 存入数据的类型
     */
    private String type;

    /**
     * 要存入redis的数据key
     */
    private String key;

    /**
     * 要存入redis的值
     */
    private T value;

    /**
     * 数据有效时长,单位为s
     */
    private Long timeout;

    /**
     * 偏移量
     */
    private Long offset;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
