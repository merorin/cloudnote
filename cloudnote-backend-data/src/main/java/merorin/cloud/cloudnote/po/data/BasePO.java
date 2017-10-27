package merorin.cloud.cloudnote.po.data;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description: 这是所有PO类的基础类
 *
 * @author guobin On date 2017/10/27.
 * @version 1.0
 * @since jdk 1.8
 */
public abstract class BasePO implements Serializable {

    private static final long serialVersionUID = 1909058172451214760L;

    /**
     * 数据的创建时间,不能参与到业务层算法之中
     */
    @Field("gmt_create")
    private LocalDateTime createTime;

    /**
     * 最后更新时间,不能参与到业务层算法之中
     */
    @Field("gmt_modified")
    private LocalDateTime lastUpdateTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
