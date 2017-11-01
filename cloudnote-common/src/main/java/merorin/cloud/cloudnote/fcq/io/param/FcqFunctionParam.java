package merorin.cloud.cloudnote.fcq.io.param;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description: 封装fcq框架需要的基础数据,多封装一个{@code Function}类型的参数
 *              如果使用这一个参数将会被调用apply方法执行
 *
 * @param <T> 传入function的参数类型
 * @param <R> 返回值类型
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqFunctionParam<T, R> extends FcqCommonParam<T> {

    /**
     * 传入的待执行函数,需要传入类型为{@code T},返回类型为{@code R}的返回结果
     */
    private Function<? super T, ? extends R> mapper;

    /**
     * 传入的待执行函数,无须传入值,返回类型为{@code R}的返回结果
     */
    private Supplier<? extends R> supplier;

    public Function<? super T, ? extends R> getMapper() {
        return mapper;
    }

    public void setMapper(Function<? super T, ? extends R> mapper) {
        this.mapper = mapper;
    }

    public Supplier<? extends R> getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier<? extends R> supplier) {
        this.supplier = supplier;
    }
}
