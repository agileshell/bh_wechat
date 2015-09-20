package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;

public class MessageListResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -5792557340118259756L;

    private List<Message> list;

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

}
