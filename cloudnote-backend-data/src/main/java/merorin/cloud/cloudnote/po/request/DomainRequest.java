package merorin.cloud.cloudnote.po.request;

import merorin.cloud.cloudnote.po.data.BasePO;

/**
 * Description: 通用的dao层请求集合,所有的request都必须继承这个类
 *              封装了客户端进行请求的参数
 *
 * @author guobin On date 2017/10/25.
 * @version 1.0
 * @since jdk 1.8
 */
public class DomainRequest<T extends BasePO> {

    /**
     * 表示需要进行查询的页码
     */
    private int page = 0;

    /**
     * 页面的大小
     */
    private int pageSize = 10;

    /**
     * 是否需要进行分页
     * true为需要,false为不需要,默认为true
     */
    private boolean needPaging = true;

    /**
     * 数据库开始查询的索引号
     */
    private int start = -1;

    /**
     * 请求中的参数封装
     */
    private T value;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNeedPaging() {
        return needPaging;
    }

    public void setNeedPaging(boolean needPaging) {
        this.needPaging = needPaging;
    }

    public int getStart() {
        return this.start < 0 ? this.start * this.pageSize : this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    /**
     * 获取数据库查询的结束位置
     * @return 结束位置
     */
    public int getEnd() {
        return (this.page + 1) * this.pageSize;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
