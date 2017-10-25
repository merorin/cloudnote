package merorin.cloud.cloudnote.result;

import com.alibaba.fastjson.JSON;
import merorin.cloud.cloudnote.common.ResultConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description: 通用的返回结果集合
 *
 * @author guobin On date 2017/10/25.
 * @param <T> 包含实际类型
 * @version 1.0
 * @since jdk 1.8
 */
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1452706754677411742L;

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
    private List<T> values = new LinkedList<>();

    /**
     * 构造器,默认构造一个错误的返回结果集合
     */
    public CommonResult() {
        this.message = ResultConstant.Message.ERROR;
        this.code = ResultConstant.Code.ERROR;
    }

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
        return new ArrayList<>(this.values);
    }

    public void setValues(List<T> values) {
        if (values != null) {
            if (!this.values.isEmpty()) {
                this.values.clear();
            }
            this.values.addAll(values);
        }
    }

    /**
     * 向结果中添加一个值
     * @param value 要添加的值
     */
    public void addValue(T value) {
        this.values.add(value);
    }

    /**
     * 从结果中获取一个值
     * @return 获取的数据
     */
    public T getValue() {
        return this.values.isEmpty() ? null : this.values.get(0);
    }

    /**
     * 判断这次处理结果是否成功
     * @return 判断结果
     */
    public boolean isSuccess() {
        return this.code == ResultConstant.Code.SUCCESS;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
