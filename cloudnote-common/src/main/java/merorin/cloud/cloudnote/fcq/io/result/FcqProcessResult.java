package merorin.cloud.cloudnote.fcq.io.result;

import merorin.cloud.cloudnote.fcq.io.common.FcqResultConstant;
import merorin.cloud.cloudnote.fcq.io.param.FcqParam;

import java.util.List;

/**
 * Description: fcq队列处理返回结果
 *
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqProcessResult {

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
    private List<FcqParam> values;

    /**
     * 构造器
     * @param code 错误码
     * @param message 信息
     */
    private FcqProcessResult(int code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * 构造一个默认的成功的返回实例
     * @return 得到的返回实例
     */
    public static FcqProcessResult success() {
        return FcqProcessResult.success(FcqResultConstant.Message.SUCCESS);
    }

    /**
     * 构造一个成功的返回实例
     * @param message 传入的信息
     * @return 得到的返回实例
     */
    public static FcqProcessResult success(String message) {
        return new FcqProcessResult(FcqResultConstant.Code.SUCCESS, message);
    }

    /**
     * 构造一个默认的失败返回实例
     * @return 得到的返回实例
     */
    public static FcqProcessResult error() {
        return FcqProcessResult.error(FcqResultConstant.Message.ERROR);
    }


    /**
     * 构造一个失败的返回实例
     * @param message 传入的信息
     * @return 得到的返回实例
     */
    public static FcqProcessResult error(String message) {
        return new FcqProcessResult(FcqResultConstant.Code.ERROR, message);
    }

    public boolean isSuccess() {
        return this.code == FcqResultConstant.Code.SUCCESS;
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

    public List<FcqParam> getValues() {
        return values;
    }

    public void setValues(List<FcqParam> values) {
        this.values = values;
    }
}
