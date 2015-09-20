package com.bh.wechat.response;

import java.io.Serializable;

public class HelpDetailResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 3979636014533521759L;

    private int helpId;

    private String title;

    private String content;

    public int getHelpId() {
        return helpId;
    }

    public void setHelpId(int helpId) {
        this.helpId = helpId;
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
