package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;

public class HelpListResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 5484693305333796241L;

    private List<Help> list;

    public List<Help> getList() {
        return list;
    }

    public void setList(List<Help> list) {
        this.list = list;
    }
}
