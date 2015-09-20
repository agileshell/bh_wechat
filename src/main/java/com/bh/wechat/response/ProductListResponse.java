package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;

public class ProductListResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -9029480744134989148L;

    private List<Product> list;

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

}
