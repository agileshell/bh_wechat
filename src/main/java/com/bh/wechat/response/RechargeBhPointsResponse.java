package com.bh.wechat.response;

import java.io.Serializable;

public class RechargeBhPointsResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 7592200481092434413L;

    private int id;

    private float amount;

    private String createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

}
