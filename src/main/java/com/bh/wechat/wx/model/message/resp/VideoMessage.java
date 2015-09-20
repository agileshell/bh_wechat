package com.bh.wechat.wx.model.message.resp;

/**
 * 视频消息
 */
public class VideoMessage extends MediaMessage {
    /**
     * 视频消息的标题
     */
    private String title;

    /**
     * 视频消息的描述
     */
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VideoMessage(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public VideoMessage() {
        super();
    }


}
