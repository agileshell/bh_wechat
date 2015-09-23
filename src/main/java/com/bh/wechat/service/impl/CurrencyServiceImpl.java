package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.ListBhPointsWithdrawHistoryRequest;
import com.bh.wechat.request.ListCurrencyDealRequest;
import com.bh.wechat.request.RechargeBhPointsRequest;
import com.bh.wechat.request.WithdrawBhPointsRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.BhPointsWithdrawHistoryResponse;
import com.bh.wechat.response.CurrencyDealHistoryResponse;
import com.bh.wechat.response.RechargeBhPointsResponse;
import com.bh.wechat.service.CurrencyService;

@Service
public class CurrencyServiceImpl extends BaseService implements CurrencyService {

    @Override
    public RechargeBhPointsResponse rechargeBhPoints(RechargeBhPointsRequest request) throws BhException {
        return httpGatewayTemplate.invoke(RechargeBhPointsResponse.class, request);
    }

    @Override
    public BaseResponse withdrawBhPoints(WithdrawBhPointsRequest request) throws BhException {
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

    @Override
    public BhPointsWithdrawHistoryResponse listBhPointsWithdrawHistory(ListBhPointsWithdrawHistoryRequest request)
            throws BhException {
        return httpGatewayTemplate.invoke(BhPointsWithdrawHistoryResponse.class, request);
    }

    @Override
    public CurrencyDealHistoryResponse listCurrencyDealHistory(ListCurrencyDealRequest request) throws BhException {
        return httpGatewayTemplate.invoke(CurrencyDealHistoryResponse.class, request);
    }

}
