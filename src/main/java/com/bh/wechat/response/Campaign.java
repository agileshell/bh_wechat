package com.bh.wechat.response;

import java.io.Serializable;

public class Campaign implements Serializable {

    private static final long serialVersionUID = 2722038910802569948L;

    private int campaignId;

    private String coverImg;

    private String title;

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
