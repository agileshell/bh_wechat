package com.bh.wechat.response;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 5683661144325284047L;

    private int messageId;

    private String title;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
