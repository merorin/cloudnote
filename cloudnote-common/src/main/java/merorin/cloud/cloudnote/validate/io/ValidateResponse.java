package merorin.cloud.cloudnote.validate.io;

import com.alibaba.fastjson.JSON;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Description: 解析结果
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public class ValidateResponse {

    /**
     * 错误信息列表
     */
    private List<String> errorMsgs = new LinkedList<>();

    /**
     * 判断是否校验成功
     * 没有错误信息即为成功
     * @return 校验结果
     */
    public boolean isSuccess() {
        return this.errorMsgs.isEmpty();
    }

    /**
     * 获取所有的错误信息列表
     * @return 错误信息列表
     */
    public List<String> getErrorMsgs() {
        return errorMsgs;
    }

    /**
     * 向返回结果中添加一条错误信息
     * @param errorMsg 传入的错误信息
     */
    public void addMsg(String errorMsg) {
        Optional.ofNullable(errorMsg).ifPresent(this.errorMsgs::add);
    }

    /**
     * 向返回结果中添加多条错误信息
     * @param errorMsgs 传入的错误信息列表
     */
    public void addAllMsg(Collection<String> errorMsgs) {
        Optional.ofNullable(errorMsgs).ifPresent(this.errorMsgs::addAll);
    }

    /**
     * 将错误信息列表转化成JSON字符串
     * @return error的JSON字符串
     */
    public String errorsToString() {
        return this.errorMsgs.isEmpty() ? "无错误信息" : JSON.toJSONString(this.errorMsgs);
    }

    /**
     * 清除所有的错误信息数据
     */
    public void clearAllMsgs() {
        this.errorMsgs.clear();
    }
}
