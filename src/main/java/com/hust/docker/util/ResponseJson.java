package com.hust.docker.util;

/**
 * @author Autubrew
 * @date 2020-02-17 20:20
 * @description
 **/

public class ResponseJson<T> {

    private T data;
    private Integer code;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
}
