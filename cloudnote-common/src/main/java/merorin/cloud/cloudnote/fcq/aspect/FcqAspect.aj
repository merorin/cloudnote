package merorin.cloud.cloudnote.fcq.aspect;

import com.alibaba.fastjson.JSON;
import merorin.cloud.cloudnote.fcq.io.exception.FcqException;
import merorin.cloud.cloudnote.fcq.io.param.FcqCommonParam;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Description: fcq队列的aspect类
 *
 * @author guobin On date 2017/11/2.
 * @since jdk 1.8
 * @version 1.0
 */
@Aspect
public aspect FcqAspect {

    private static final Charset UTF8 = Charset.forName("utf-8");

    /**
     * Pointcut定义切点函数,目前路径直接进行硬编码,不采用xml来进行配置
     * 拦截目标及其所有子类
     * 在所有{@link merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor}的方法执行之前进行拦截
     * 对传入的参数进行校验,判断其是否有效
     *
     * @param param 传入的参数
     * @param <T> 参数的类型
     */
    @Pointcut("execution(public * merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor+.*()) && args(param)")
    private <T> void fcqProcessorImplPointcut(FcqCommonParam<T> param){}

    /**
     * Pointcut定义切点函数,目前路径直接进行硬编码,不采用xml来进行配置
     * 拦截目标类
     * 在所有{@link merorin.cloud.cloudnote.fcq.core.impl.FcqQueueProcessorContainer}的方法执行之前进行拦截
     * 对传入的参数进行计算并生成校验码,判断其是否有效
     *
     * @param param 传入的参数
     * @param <T> 参数的类型
     */
    @Pointcut("execution(public * merorin.cloud.cloudnote.fcq.core.impl.FcqQueueProcessorContainer.*()) && args(param)")
    private <T> void fcqProcessorContainerPointcut(FcqCommonParam<T> param) {}

    /**
     * 校验token
     * @param param 参数
     * @param <T> 参数封装的核心数据类型
     */
    @Before(value = "fcqProcessorImplPointcut(param)", argNames = "param")
    public <T> void checkToken(FcqCommonParam<T> param) {
        //根据param生成一个校验码
        Optional.ofNullable(param).map(FcqCommonParam::getToken).ifPresent(this::checkToken);
    }

    /**
     * 设置token
     * @param param 参数
     * @param <T> 参数封装的核心数据类型
     */
    @Before(value = "fcqProcessorContainerPointcut(param)", argNames = "param")
    public <T> void setToken(FcqCommonParam<T> param) {

        //对param进行校验
        Optional.ofNullable(param).map(this::buildToken).ifPresent(param::setToken);
    }

    /**
     * 根据参数简单计算生成一个token
     * @param param 参数
     * @param <T> 参数封装的核心数据类型
     * @return 构造出来的token字符串
     */
    private <T> byte[] buildToken(FcqCommonParam<T> param) {

        return Optional.ofNullable(param).map(JSON::toJSONString).map(this::doBuildTokenObj).orElse(null);
    }

    /**
     * 对token进行解码计算
     * @param token 传入的token
     */
    private void checkToken(byte[] token) {

        boolean success = Optional.ofNullable(token)
                .map(arr -> new String(arr,UTF8))
                .map(this::parseToken)
                .orElse(false);

        if (!success) {
            throw new FcqException("Please do call the functions of queue-processor via queueProcessorContainer.");
        }
    }

    /**
     * 构造一个token对象
     * @param str 传入的字符串
     * @return 返回结果
     */
    private byte[] doBuildTokenObj(String str) {

        Token token = new Token();
        token.setParamJSONStr(str);
        token.setStrHashCode(str.hashCode());

        return token.toString().getBytes(UTF8);
    }

    /**
     * 解析token
     * @param str 传入的字符串
     * @return 返回结果
     */
    private boolean parseToken(String str) {
        Token tokenObj = JSON.parseObject(str, Token.class);

        int hashCode = tokenObj.getStrHashCode();

        return tokenObj.getParamJSONStr().hashCode() == hashCode;
    }

    /**
     * 私有类,作为一个token
     */
    private static class Token {

        private String paramJSONStr;

        private int strHashCode;

        public String getParamJSONStr() {
            return paramJSONStr;
        }

        public void setParamJSONStr(String paramJSONStr) {
            this.paramJSONStr = paramJSONStr;
        }

        public int getStrHashCode() {
            return strHashCode;
        }

        public void setStrHashCode(int strHashCode) {
            this.strHashCode = strHashCode;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}
