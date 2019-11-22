package com.hqhop.config.dingtalk.dingtalkVo;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 * author:chengy
 */
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 3068837394742385883L;
    /**
     * 错误码
     **/
    private Integer code;

    /**
     * 提示信息
     **/
    private String msg;

    /**
     * 总记录数，只有在分页查询的结果中需要
     */
    private Long totalCount;

    /**
     * 具体内容
     **/
    private T data;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
