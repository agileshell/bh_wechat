package com.bh.wechat.response;

import java.io.Serializable;

public class AccountResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -785445734624099391L;

    private String token;

    private String refreshToken;

    private int userId;

    private String userName;

    private int bhPoints;

    private int dzPoints;

    private int qianPoints;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBhPoints() {
        return bhPoints;
    }

    public void setBhPoints(int bhPoints) {
        this.bhPoints = bhPoints;
    }

    public int getDzPoints() {
        return dzPoints;
    }

    public void setDzPoints(int dzPoints) {
        this.dzPoints = dzPoints;
    }

    public int getQianPoints() {
        return qianPoints;
    }

    public void setQianPoints(int qianPoints) {
        this.qianPoints = qianPoints;
    }

}
