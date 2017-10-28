package merorin.cloud.cloudnote.bo.result;

import com.alibaba.fastjson.JSON;
import merorin.cloud.cloudnote.common.ResultConstant;

/**
 * Description: 通用bo层的返回结果集合
 *
 * @author guobin On date 2017/10/28.
 * @version 1.0
 * @since jdk 1.8
 */
public class CommonBusinessResult {

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

    public CommonBusinessResult() {
        this(ResultConstant.Code.ERROR, ResultConstant.Message.ERROR);
    }

    public CommonBusinessResult(int code, String message) {
        this.code = code;
        this.message = message;
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
