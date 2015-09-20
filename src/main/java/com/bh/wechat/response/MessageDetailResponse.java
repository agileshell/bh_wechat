package com.bh.wechat.response;

import java.io.Serializable;

public class MessageDetailResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 6621180157700565680L;

    private int messageId;

    private String title;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
