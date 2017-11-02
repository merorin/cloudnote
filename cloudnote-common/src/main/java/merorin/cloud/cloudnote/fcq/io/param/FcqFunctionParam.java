package merorin.cloud.cloudnote.fcq.io.param;

import merorin.cloud.cloudnote.fcq.io.common.Performer;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description: 封装fcq框架需要的基础数据,多封装一个{@code Function}类型的参数
 *              如果使用这一个参数将会被调用apply方法执行
 *
 * @param <T> 传入function的参数类型,如果无须传参则用{@link merorin.cloud.cloudnote.fcq.io.common.FcqFuncTemp}替代
 * @param <R> 返回值类型,如果无返回值则用{@link merorin.cloud.cloudnote.fcq.io.common.FcqFuncTemp}替代
 *
 * @author guobin On date 2017/10/31.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqFunctionParam<T, R> extends FcqCommonParam<T> {

    /**
     * 函数的类型
     */
    public enum FuncType {

        /**
         * 传入的方法类型是{@code Function}
         * {@code Function} 传入值,返回结果
         */
        FUNCTION,

        /**
         * 传入的方法类型是{@code Supplier}
         * {@code Supplier} 不传入值,返回结果
         */
        SUPPLIER,

        /**
         * 传入的方法类型是{@code Consumer}
         * {@code Consumer} 传入值,不返回结果
         */
        CONSUMER,

        /**
         * 传入的方法类型是{@code Performer}
         * {@code Performer} 不传入值,不返回结果
         */
        PERFORMER
    }

    /**
     * 函数类型
     */
    private FuncType funcType;

    /**
     * 传入的待执行函数,需要传入类型为{@code T},返回类型为{@code R}的返回结果
     */
    private Function<? super T, ? extends R> mapper;

    /**
     * 传入的待执行函数,无须传入值,返回类型为{@code R}的返回结果
     */
    private Supplier<? extends R> supplier;

    /**
     * 传入的待执行函数,传入类型为{@code T}的值,不返回结果
     */
    private Consumer<? super T> consumer;

    /**
     * 传入的待执行函数,不传入值,也不返回结果
     */
    private Performer performer;

    public FuncType getFuncType() {
        return funcType;
    }

    public void setFuncType(FuncType funcType) {
        this.funcType = funcType;
    }

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

    public Consumer<? super T> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<? super T> consumer) {
        this.consumer = consumer;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }
}
