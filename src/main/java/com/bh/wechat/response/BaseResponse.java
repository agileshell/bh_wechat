package com.bh.wechat.response;

import java.io.Serializable;

import com.bh.wechat.util.JacksonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 7768151032361724053L;

    private int ret;

    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ret == 0;
    }

    @Override
    public String toString() {
        return JacksonUtils.stringify(this);
    }
}