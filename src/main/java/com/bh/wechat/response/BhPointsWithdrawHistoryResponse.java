package com.bh.wechat.response;

import java.util.List;

public class BhPointsWithdrawHistoryResponse extends BaseResponse {

    private static final long serialVersionUID = -7842575955160454584L;

    private List<BhPointsWithdrawHistory> list;

    public List<BhPointsWithdrawHistory> getList() {
        return list;
    }

    public void setList(List<BhPointsWithdrawHistory> list) {
        this.list = list;
    }

}
