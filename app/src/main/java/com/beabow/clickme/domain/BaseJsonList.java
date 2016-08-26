package com.beabow.clickme.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.enjoydecorate.domain
 * 创建者： lx
 * 创建时间：2016/4/8 19:04
 * 描述: TODO
 */
public class BaseJsonList<T> implements Serializable {
    private static final long serialVersionUID = -3440061414071692254L;
    private String success;
    private String msg;
    private List<T> data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public List<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

}
