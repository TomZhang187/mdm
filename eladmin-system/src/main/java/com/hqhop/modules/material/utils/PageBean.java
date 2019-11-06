package com.hqhop.modules.material.utils;

import lombok.Data;

import java.util.List;
@Data
public class PageBean<T> {

    private List<T> content;
    //private List<Object> tableBeans;用object也可以
    private Integer currPage;
    /**
     * 总页数
     */
    private Integer totalPages;
    /**
     * 数据总条数
     */
    private Integer totalElements;
    /**
     * 每页显示数据条数
     */
    private Integer size;

    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public PageBean(List<T> content, Integer currPage, Integer totalPage) {
        this.content = content;
        this.currPage = currPage;
        this.totalPages = totalPage;
    }

    public PageBean() {
    }


}
