package com.bh.wechat.response;

import java.io.Serializable;

public class Help implements Serializable {

    private static final long serialVersionUID = -8456386106123940446L;

    private int helpId;

    private String title;

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
}
