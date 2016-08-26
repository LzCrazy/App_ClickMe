package com.beabow.clickme.domain;

import java.io.Serializable;

/**
 * Created by luoxiong on 2016/4/7.
 */
public class BaseJson<T> implements Serializable{
    private static final long serialVersionUID = -3440061414071692254L;
    private  String success;
    private  String msg;
    private  T data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
