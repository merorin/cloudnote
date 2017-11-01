package merorin.cloud.cloudnote.common;

/**
 * Description: 存入redis的数据类型的枚举类
 *
 * @author guobin On date 2017/10/30.
 * @version 1.0
 * @since jdk 1.8
 */
public enum RedisTypeEnum {

    /**
     * 存入redis的是数据
     */
    DATA,

    /**
     * 存入redis的是索引表
     */
    INDEX;
}
