package merorin.cloud.cloudnote.fcq.io.result;

import java.util.List;

/**
 * Description: fcq队列处理返回结果
 *
 * @param <T> 返回数据的类型
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqProcessResult<T> {

    /**
     * 处理结果的返回码
     */
    private int code;

    /**
     * 处理结果的返回信息
     */
    private String message;

    /**
     * 数据的数量
     */
    private int count;

    /**
     * 数据总数
     */
    private int totalCount;

    /**
     * 异常信息
     */
    private String exceptionMsg;

    /**
     * 当前查询的页码
     */
    private int page;

    /**
     * 返回的数据
     */
    private List<T> values;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
