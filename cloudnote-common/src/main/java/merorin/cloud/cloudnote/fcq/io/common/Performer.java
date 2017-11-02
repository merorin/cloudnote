package merorin.cloud.cloudnote.fcq.io.common;

/**
 * Description: 是一个函数式接口,不接受参数,也不返回结果
 *
 * @author guobin On date 2017/11/2.
 * @version 1.0
 * @since jdk 1.8
 */
@FunctionalInterface
public interface Performer {

    /**
     * 执行一个无传参无返回的函数
     */
    void execute();
}
