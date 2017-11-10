package merorin.cloud.cloudnote.validate.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Description: 函数式参数校验者
 *
 * @author guobin On date 2017/11/9.
 * @version 1.0
 * @since jdk 1.8
 */
public class FuncValidator<T> {

    /**
     * 默认无错误码传入时对应的错误码字符串
     */
    private static final String NO_ERROR_CODE  = "";

    /**
     * 要校验的参数对象
     */
    private final T value;

    /**
     * 校验对象为空时返回的错误码
     */
    private final String nullParamCode;

    /**
     * 存储所有的错误信息
     * 如果校验最终结果无错,那么这个list为空
     */
    private final List<ErrorEntry> errors = new LinkedList<>();

    /**
     * 验证成功返回调用的函数(带返回值)
     */
    private Function<T, ?> successMapper;

    /**
     * 验证失败返回调用的函数(带返回值)
     */
    private Function<FuncValidator<T>, ?> errorMapper;

    /**
     * 验证成功的返回调用函数(不带返回值)
     */
    private Consumer<T> successConsumer;

    /**
     * 验证失败的返回调用函数(不带返回值)
     */
    private Consumer<FuncValidator<T>> errorConsumer;

    /**
     * 构造一个验证者实例
     * @param value 传入的值
     */
    private FuncValidator(T value, String nullParamCode) {
        this.value = value;
        this.nullParamCode = nullParamCode;
    }

    /**
     * 返回一个带传入值的{@code FuncValidator}
     *
     * @param value 带校验的对象
     * @param <T> 对象的类型
     * @return 一个校验者
     */
    public static <T> FuncValidator<T> of(T value) {
        return of(value, NO_ERROR_CODE);
    }

    /**
     * 返回一个带传入值的{@code FuncValidator}
     *
     * @param value 带校验的对象
     * @param <T> 对象的类型
     * @param nullParamCode 当校验对象为空时返回的错误码
     * @return 一个校验者
     * @throws NullPointerException 当传入的空对象错误码为空时抛出异常
     */
    public static <T> FuncValidator<T> of(T value, String nullParamCode) {
        if (Objects.isNull(nullParamCode)) {
            throw new NullPointerException("Error code of null param is missing!");
        }
        return new FuncValidator<>(value, nullParamCode);
    }

    /**
     * 判断mapper返回值非空
     * @param mapper 传入的mapper
     * @param errorMsg 错误信息
     * @param <R> mapper返回的类型
     * @return 返回更新后的校验者
     */
    public <R> FuncValidator<T> notNull(Function<? super T, ? extends R> mapper, String errorMsg) {
        return this.notNull(mapper, errorMsg, NO_ERROR_CODE);
    }

    /**
     * 判断mapper返回值非空
     * @param mapper 传入的mapper
     * @param errorMsg 错误信息
     * @param errorCode 错误码
     * @param <R> mapper返回的类型
     * @return 返回更新后的校验者
     */
    public <R> FuncValidator<T> notNull(Function<? super T, ? extends R> mapper, String errorMsg, String errorCode) {
        if (Objects.nonNull(this.value) && Objects.isNull(mapper.apply(this.value))) {
            this.errors.add(new ErrorEntry(errorMsg, errorCode));
        }
        return this;
    }

    /**
     * 自定义校验方式
     * @param predicate 传入的断言
     * @param errorMsg 错误信息
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> on(Predicate<? super T> predicate, String errorMsg) {
        return this.on(predicate, errorMsg, NO_ERROR_CODE);
    }

    /**
     * 自定义校验方式
     * @param predicate 传入的断言
     * @param errorMsg 错误信息
     * @param errorCode 错误码
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> on(Predicate<? super T> predicate, String errorMsg, String errorCode) {
        if (Objects.nonNull(this.value) && predicate.test(this.value)) {
            this.errors.add(new ErrorEntry(errorMsg, errorCode));
        }
        return this;
    }

    /**
     * 自定义校验方式,当满足条件时才机型校验
     * @param predicate 校验断言
     * @param errorMsg 错误信息
     * @param condition 校验的条件
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> onIf(Predicate<? super T> predicate, String errorMsg, Predicate<? super T> condition) {
        return this.onIf(predicate, errorMsg, NO_ERROR_CODE, condition);
    }

    /**
     * 自定义校验方式,当满足条件时才机型校验
     * @param predicate 校验断言
     * @param errorMsg 错误信息
     * @param condition 校验的条件
     * @param errorCode 错误码                 
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> onIf(Predicate<? super T> predicate, String errorMsg, String errorCode, Predicate<? super T> condition) {
        if (Objects.nonNull(this.value) && condition.test(this.value) && predicate.test(this.value)) {
            this.errors.add(new ErrorEntry(errorMsg, errorCode));
        }
        return this;
    }

    /**
     * 添加验证成功的回调方法
     * @param mapper 执行的函数,会返回值
     * @return 返回更新后的校验者
     */
    public <R> FuncValidator<T> success(Function<T, ? extends R> mapper) {
        if (Objects.nonNull(this.value) && Objects.nonNull(mapper) && Objects.isNull(this.successMapper)) {
            this.successMapper = mapper;
        }
        return this;
    }

    /**
     * 添加验证成功的回调方法
     * @param consumer 执行的函数,不会返回值
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> success(Consumer<T> consumer) {
        if (Objects.nonNull(this.value) && Objects.nonNull(consumer) && Objects.isNull(this.successConsumer)) {
            this.successConsumer = consumer;
        }
        return this;
    }

    /**
     * 添加验证失败的回调方法
     * @param mapper 执行的失败函数,会返回值
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> error(Function<FuncValidator<T>, ?> mapper) {
        if (Objects.nonNull(this.value) && Objects.nonNull(mapper) && Objects.isNull(this.errorMapper)) {
            this.errorMapper = mapper;
        }
        return this;
    }

    /**
     * 添加验证失败的回调方法
     * @param consumer 执行的失败函数,不会返回值
     * @return 返回更新后的校验者
     */
    public FuncValidator<T> error(Consumer<FuncValidator<T>> consumer) {
        if (Objects.nonNull(this.value) && Objects.nonNull(consumer) && Objects.isNull(this.errorConsumer)) {
            this.errorConsumer = consumer;
        }
        return this;
    }

    /**
     * 执行验证,返回结果!
     * @param <R> 返回类的类型
     * @return 回调函数执行的结果
     * @throws NullPointerException 如果成功函数或者失败函数尚未被定义,就会抛出验证异常
     */
    @SuppressWarnings("unchecked")
    public <R> R withValidation() {
        this.preValidate();
        if (Objects.isNull(this.successMapper)) {
            throw new NullPointerException("Method 'success' must be called before 'withValidation'!");
        } else if (Objects.isNull(this.errorMapper)) {
            throw new NullPointerException("Method 'error' must be called before 'withValidation'!");
        }
        return (R) (this.errors.isEmpty() ? this.successMapper.apply(this.value) : this.errorMapper.apply(this)) ;
    }

    /**
     * 执行验证,不会返回值
     * @throws NullPointerException 如果成功函数或者失败函数尚未被定义,就会抛出验证异常
     */
    public void voidValidation(){
        this.preValidate();
        if (Objects.isNull(this.successConsumer)) {
            throw new NullPointerException("Method 'success' must be called before 'voidValidation'!");
        } else if (Objects.isNull(this.errorConsumer)) {
            throw new NullPointerException("Method 'error' must be called before 'voidValidation'!");
        } else if (this.errors.isEmpty()) {
            this.successConsumer.accept(this.value);
        } else {
            this.errorConsumer.accept(this);
        }
    }

    /**
     * 获取错误信息
     * @return 错误信息列表
     */
    public final List<ErrorEntry> getErrors() {
        return new ArrayList<>(this.errors);
    }

    /**
     * 在执行{@link this#withValidation}和{@link this#voidValidation}前进行的预校验
     * 如果{@link this#value}为空则会向{@link this#errors}中添加一条错误信息
     */
    private void preValidate() {
        if (Objects.isNull(this.value)) {
            this.errors.add(new ErrorEntry(this.nullParamCode, "The value need validating is missing!"));
        }
    }

    /**
     * 验证失败封装类
     */
    static class ErrorEntry {

        /**
         * 错误码
         */
        final String code;

        /**
         * 错误信息
         */
        final String message;

        ErrorEntry(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public final String getCode() {
            return code;
        }

        public final String getMessage() {
            return message;
        }
    }
}
