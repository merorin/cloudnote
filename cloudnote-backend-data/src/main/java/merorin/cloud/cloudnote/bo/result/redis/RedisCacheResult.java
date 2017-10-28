package merorin.cloud.cloudnote.bo.result.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: 封装redis的返回结果
 *
 * @author guobin On date 2017/10/28.
 * @version 1.0
 * @since jdk 1.8
 */
public class RedisCacheResult<T> {

    /**
     * 得到的值
     */
    private List<T> valueList;

    /**
     * 得到的值
     */
    private Set<T> valueSet;

    /**
     * 返回的keySet
     */
    private Set<String> keys;

    /**
     * 返回的map集合
     */
    private Map<String, T> valueMap;

    public List<T> getValueList() {
        return valueList;
    }

    public void setValueList(List<T> valueList) {
        this.valueList = valueList;
    }

    public Set<T> getValueSet() {
        return valueSet;
    }

    public void setValueSet(Set<T> valueSet) {
        this.valueSet = valueSet;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public void setKeys(Set<String> keys) {
        this.keys = keys;
    }

    public Map<String, T> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, T> valueMap) {
        this.valueMap = valueMap;
    }
}
