package merorin.cloud.cloudnote.validate.annotation;

import merorin.cloud.cloudnote.validate.validator.Validateable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 被打上这个注解的字段或者参数会被进行参数校验
 *
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Validate {

    /**
     * 这个参数对应的校验类数组
     * 表示这个参数的值会被数组中所有的校验类进行校验
     * 校验类为自定义的校验类
     */
    Class<? extends Validateable>[] value();
}
