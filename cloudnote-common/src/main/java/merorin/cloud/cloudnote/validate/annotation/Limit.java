package merorin.cloud.cloudnote.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 对字段值的限制类注解
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Limit {

    /**
     * 字段长度的最小值限制
     * 默认值为0
     */
    long min() default 0L;

    /**
     * 字段长度的最大值限制
     * 默认值为-1,表示不限制最大
     */
    long max() default -1L;
}
