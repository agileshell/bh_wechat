package com.bh.wechat.response;

import java.util.List;

public class CurrencyDealHistoryResponse extends BaseResponse {

    private static final long serialVersionUID = -5402928191502415286L;

    private List<CurrencyDealHistory> list;

    public List<CurrencyDealHistory> getList() {
        return list;
    }

    public void setList(List<CurrencyDealHistory> list) {
        this.list = list;
    }

}
