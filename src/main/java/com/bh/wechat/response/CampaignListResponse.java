package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;

public class CampaignListResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 7013008970807478127L;

    private List<Campaign> list;

    public List<Campaign> getList() {
        return list;
    }

    public void setList(List<Campaign> list) {
        this.list = list;
    }

}
