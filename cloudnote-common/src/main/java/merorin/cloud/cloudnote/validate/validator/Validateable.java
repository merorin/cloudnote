package merorin.cloud.cloudnote.validate.validator;

import java.util.Set;

/**
 * Description: 自定义可验证接口
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public interface Validateable {

    /**
     * 对数据进行校验
     * @param fieldName 字段名
     * @param value 需要进行校验的值
     * @return 校验结果,封装的错误信息set
     */
    Set<String> test(String fieldName, Object value);
}
