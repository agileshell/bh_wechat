package com.bh.wechat.wx.model.message.resp;

/**
 * 多媒体消息<br>
 * 图片消息、语音消息直接用此类
 */
public class MediaMessage extends BaseMessage {
    /**
     * 通过上传多媒体文件，得到的id
     */
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
