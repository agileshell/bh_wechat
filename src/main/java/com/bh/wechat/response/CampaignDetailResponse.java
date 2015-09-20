package com.bh.wechat.response;

import java.io.Serializable;

public class CampaignDetailResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 6442258598567194491L;

    private int campaignId;

    private String coverImg;

    private String title;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
