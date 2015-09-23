package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.ListBhPointsWithdrawHistoryRequest;
import com.bh.wechat.request.ListCurrencyDealRequest;
import com.bh.wechat.request.RechargeBhPointsRequest;
import com.bh.wechat.request.WithdrawBhPointsRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.BhPointsWithdrawHistoryResponse;
import com.bh.wechat.response.CurrencyDealHistoryResponse;
import com.bh.wechat.response.RechargeBhPointsResponse;

public interface CurrencyService {

    RechargeBhPointsResponse rechargeBhPoints(RechargeBhPointsRequest request) throws BhException;

    BaseResponse withdrawBhPoints(WithdrawBhPointsRequest request) throws BhException;

    BhPointsWithdrawHistoryResponse listBhPointsWithdrawHistory(ListBhPointsWithdrawHistoryRequest request) throws BhException;

    CurrencyDealHistoryResponse listCurrencyDealHistory(ListCurrencyDealRequest request) throws BhException;
}
