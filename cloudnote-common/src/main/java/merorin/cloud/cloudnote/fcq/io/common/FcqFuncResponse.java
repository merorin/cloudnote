package merorin.cloud.cloudnote.fcq.io.common;

import com.alibaba.fastjson.JSON;

import java.util.Optional;

/**
 * Description: fcq函数方法处理返回结果
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqFuncResponse {

    /**
     * function调用结果状态
     */
    private final boolean execFlag;

    /**
     * function调用结果的返回结果
     */
    private final String execRes;

    /**
     * 信息常量
     */
    public static class Msg {

        /**
         * 返回的值是null
         */
        public static final String EMPTY_MESSAGE = "Empty response";

        /**
         * 参数非法
         */
        public static final String INVALID_PARAM = "Invalid function";

        /**
         * 改方法是void方法,不返回值
         */
        public static final String VOID_RESPONSE = "Void return";

    }

    /**
     * 构造器,不允许外界实例化
     * @param execFlag 执行结果状态
     * @param execRes 执行结果时间
     */
    private FcqFuncResponse(boolean execFlag, String execRes) {
        this.execFlag = execFlag;
        this.execRes = execRes;
    }

    /**
     * 创建一个成功的实例
     * @param execRes 函数的执行返回结果
     * @return 函数执行成功的响应对象
     */
    public static FcqFuncResponse success(Object execRes) {
        return new FcqFuncResponse(true, JSON.toJSONString(execRes));
    }

    /**
     * 创建一个失败的实例
     * @param execRes 函数执行失败的信息
     * @return 函数执行失败的响应对象
     */
    public static FcqFuncResponse error(String execRes) {
        return new FcqFuncResponse(false, execRes);
    }

    /**
     * 判断函数是否执行成功的标识
     * @return 处理结果
     */
    public boolean isSuccess() {
        return this.execFlag;
    }

    /**
     * 获取函数执行失败结果封装的信息
     * @return 得到的信息
     */
    public String getErrorMsg() {
        return this.execRes;
    }

    /**
     * 获取函数执行成功结果封装的对象
     * @param clazz 对象的类
     * @param <T> 对象类型
     * @return JSON解析之后的对象
     */
    public <T> T getResponseObj(Class<T> clazz) {
        return Optional.ofNullable(this.execRes)
                .map(resStr -> JSON.parseObject(resStr, clazz))
                .orElse(null);
    }
}
