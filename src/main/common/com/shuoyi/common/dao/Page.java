package com.shuoyi.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaosy-c on 2017/8/22.
 */
public class Page implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //存放结果集
    private List result;
    private static final int DEF_PAGE_VIEW_SIZE = 20;
    private int page;
    private int pageSize;
    private int count;
    private int pages;

    public Page(int count, int page, int pageSize) {
        this.count = (count < 0 ? 0 : count);
        this.page = (page <= 0 ? 1 : page);
        this.pageSize = (pageSize <= 0 ? DEF_PAGE_VIEW_SIZE : pageSize);
        this.pages = (count + getPageSize() - 1) / getPageSize();
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public int getPage() {
        return page <= 0 ? 1 : page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize <= 0 ? DEF_PAGE_VIEW_SIZE : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = (count < 0 ? 0 : count);
        if (this.count == 0)
            page = 0;
    }

    public int getPages() {
        return pages;
    }

    public int getStartNo() {
        return (getPage() - 1) * getPageSize();
    }

    public int getEndNo() {
        return Math.min(getPage() * getPageSize(), count);
    }

    public int getPrePageNo() {
        return Math.max(getPage() - 1, 1);
    }

    public int getNextPageNo() {
        return Math.min(getPage() + 1, getPages());
    }
}

