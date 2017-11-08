package merorin.cloud.cloudnote.validate.core;

import merorin.cloud.cloudnote.utils.StringUtils;
import merorin.cloud.cloudnote.validate.annotation.Limit;
import merorin.cloud.cloudnote.validate.annotation.NotNull;
import merorin.cloud.cloudnote.validate.annotation.Validate;
import merorin.cloud.cloudnote.validate.io.ValidateResponse;
import merorin.cloud.cloudnote.validate.parse.AnnotationParser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Description: 参数校验者
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public class Validator {

    /**
     * 这个方法基于字段上的注解,没有打上注解的字段将不会执行验证
     * 对传入的对象数组进行校验
     *
     * 注解如下:
     * @see NotNull
     * @see Limit
     * @see Validate
     *
     * @param methodName 校验的方法名
     * @param params 传入的对象
     *               如果传入的是null则
     * @return 校验的结果
     */
    public static ValidateResponse validate(String methodName, Object... params) {
        final ValidateResponse response = new ValidateResponse();

        final List<String> errors = Optional.ofNullable(params)
                .map(args -> Validator.doIfParamNotNull(methodName, args))
                .orElse(Collections.singletonList("Params waiting to be validated must not be null!"));

        response.addAllMsg(errors);

        return response;
    }

    /**
     * 如果传入的参数不为空,进行下一步的参数校验
     * @param methodName 校验的方法名
     * @param params 传入的参数
     * @return 错误信息列表
     */
    private static List<String> doIfParamNotNull(String methodName ,Object[] params) {
        final List<String> errors = new LinkedList<>();

        for (Object param : params) {
            final List<String> list = Optional.ofNullable(param)
                    .map(arg -> AnnotationParser.parseAnn(methodName, arg))
                    .orElse(Collections.singletonList(StringUtils.replaceArgs("Param {0} cannot be null.", params)));
            errors.addAll(list);
        }

        return errors;
    }

}
