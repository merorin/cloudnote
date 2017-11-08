package merorin.cloud.cloudnote.validate.validator.impl;

import merorin.cloud.cloudnote.utils.StringUtils;
import merorin.cloud.cloudnote.validate.validator.Validateable;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: 非空校验者
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public class NotNullValidator implements Validateable {

    @Override
    public Set<String> test(String fieldName, Object value) {
        Set<String> set = new HashSet<>(1);
        if (value == null) {
            set.add(StringUtils.replaceArgs("Filed:{0} cannot be null!", fieldName));
        }
        return set;
    }
}
