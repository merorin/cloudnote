package merorin.cloud.cloudnote.validate.parse;

import merorin.cloud.cloudnote.validate.annotation.Limit;
import merorin.cloud.cloudnote.validate.annotation.NotNull;
import merorin.cloud.cloudnote.validate.annotation.Validate;
import merorin.cloud.cloudnote.validate.validator.Validateable;
import merorin.cloud.cloudnote.validate.validator.impl.LimitValidator;
import merorin.cloud.cloudnote.validate.validator.impl.NotNullValidator;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Description: 注解的解析类
 *
 * @author guobin On date 2017/11/8.
 * @version 1.0
 * @since jdk 1.8
 */
public class AnnotationParser {

    /**
     * 解析注解
     * @param param 传入的参数
     * @return 解析的结果
     */
    public static List<String> parseAnn(Object param) {
        final List<String> errMsgs = new LinkedList<>();

        final Field[] fields = param.getClass().getFields();
        for (Field field : fields) {
            try {
                final Object fieldValue = field.get(param);
                final String fieldName = field.getName();
                //校验@NotNull
                if (field.isAnnotationPresent(NotNull.class)) {
                    Optional.ofNullable(AnnotationParser.parseNonNull(fieldName, fieldValue))
                            .ifPresent(errMsgs::addAll);
                }
                //校验@Limit
                Optional.ofNullable(field.getAnnotation(Limit.class))
                        .map(ann -> AnnotationParser.parseLimit(fieldName, fieldValue, ann))
                        .ifPresent(errMsgs::addAll);
                //校验@Validate
                Optional.ofNullable(field.getAnnotation(Validate.class))
                        .map(ann -> AnnotationParser.parseValidate(fieldName, fieldValue, ann))
                        .ifPresent(errMsgs::addAll);
            } catch (IllegalAccessException e) {
                errMsgs.add(e.getMessage());
            }
        }

        return errMsgs;
    }

    /**
     * 解析NonNull注解
     * @param fieldName 字段名
     * @param fieldValue 字段的值
     * @return 返回的错误信息,如果无错则返回null
     */
    private static Set<String> parseNonNull(String fieldName, Object fieldValue) {
        return new NotNullValidator().test(fieldName, fieldValue);
    }

    /**
     * 解析Limit注解
     * @param fieldName 字段名
     * @param fieldValue 字段的值
     * @param ann 注解
     * @return 返回的错误信息,如果无错则返回null
     */
    private static Set<String> parseLimit(String fieldName, Object fieldValue, Limit ann) {
        return new LimitValidator(ann.min(), ann.max()).test(fieldName, fieldValue);
    }

    /**
     * 解析Validate注解
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @param ann 注解
     * @return 返回的错误信息,如果无错则返回null
     */
    private static Set<String> parseValidate(String fieldName, Object fieldValue, Validate ann) {
        Set<String> msgSet = new HashSet<>();
        final Class<? extends Validateable>[] clazzArr = ann.value();
        for (Class<? extends Validateable> clazz : clazzArr) {
            try {
                msgSet.addAll(clazz.newInstance().test(fieldName,fieldValue));
            } catch (Exception e) {
                msgSet.add(e.getMessage());
            }
        }

        return msgSet;
    }
}
