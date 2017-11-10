package merorin.cloud.cloudnote.fcq.util;

/**
 * Description: 是一个函数式接口,不接受参数,也不返回结果
 *              外界如果想要将一个执行方法传入队列之时,就必须将方法封装在{@link this#execute}方法内
 *
 * @author guobin On date 2017/11/2.
 * @version 1.0
 * @since jdk 1.8
 */
@FunctionalInterface
public interface FcqFuncPerformer {

    /**
     * 执行一个无传参无返回的函数
     */
    void execute();
}
