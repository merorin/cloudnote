package merorin.cloud.cloudnote.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 非空校验注解
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NotNull {

    /**
     * 表示在哪些方法里这个参数校验会被忽视
     */
    String[] skip();
}
