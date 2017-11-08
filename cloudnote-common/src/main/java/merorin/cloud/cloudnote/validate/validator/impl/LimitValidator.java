package merorin.cloud.cloudnote.validate.validator.impl;

import merorin.cloud.cloudnote.utils.StringUtils;
import merorin.cloud.cloudnote.validate.exception.ValidationException;
import merorin.cloud.cloudnote.validate.validator.Validateable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Description: 数据长度或者大小限制校验器
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public class LimitValidator implements Validateable {

    /**
     * 默认最大值,当{@code max}为默认最大值时,max将不参与判断校验
     */
    private static final long DEFAULT_MAX = -1L;

    /**
     * 最小值
     */
    private final long min;

    /**
     * 最大值
     */
    private final long max;

    public LimitValidator(long min, long max) {
        if (max != DEFAULT_MAX && max < min) {
            throw new ValidationException("Invalid input of min value," + min + " and max value," + max + " for LimitValidator.");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public Set<String> test(String fieldName, Object value) {

        final Set<String> set = new HashSet<>(1);

        Optional.ofNullable(value).ifPresent(item -> {
            final boolean flag;
            if (item instanceof String) {
                int length = ((String) item).length();
                flag = length >= this.min & (this.max == DEFAULT_MAX || length <= this.max);
            } else if (item instanceof Integer) {
                int num = (Integer) item;
                flag = num >= this.min & (this.max == DEFAULT_MAX || num <= this.max);
            } else if (item instanceof Long) {
                long num = (Long) item;
                flag = num >= this.min & (this.max == DEFAULT_MAX || num <= this.max);
            } else {
                throw new ValidationException("Unsupported type for LimitValidator.");
            }

            if (!flag) {
                String msg = StringUtils.replaceArgs("Invalid filed value.['field':'{0}','value':'{1}','min':'{2}','max':'{3}']",
                        fieldName, value, this.min, this.max);
                set.add(msg);
            }
        });

        return set;
    }


}
