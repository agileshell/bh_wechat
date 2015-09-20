package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.GatewayProxyRequest;
import com.bh.wechat.response.CampaignDetailResponse;
import com.bh.wechat.response.CampaignListResponse;
import com.bh.wechat.response.HelpDetailResponse;
import com.bh.wechat.response.HelpListResponse;
import com.bh.wechat.service.CommonService;

@Service
public class CommonServiceImpl extends BaseService implements CommonService {

    @Override
    public CampaignListResponse listCampaigns() throws BhException {
        GatewayProxyRequest<CampaignListResponse> req =
                new GatewayProxyRequest<CampaignListResponse>().responseType(CampaignListResponse.class).apiUri(
                        ApiUri.GET_CAMPAIGNS);
        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public CampaignDetailResponse getCampaignDetail(int campaignId) throws BhException {
        GatewayProxyRequest<CampaignDetailResponse> req =
                new GatewayProxyRequest<CampaignDetailResponse>().responseType(CampaignDetailResponse.class)
                        .apiUri(ApiUri.GET_CAMPAIGN_DETAIL).uriVariable("campaignId", campaignId);
        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public HelpListResponse listHelps() throws BhException {
        GatewayProxyRequest<HelpListResponse> req =
                new GatewayProxyRequest<HelpListResponse>().responseType(HelpListResponse.class).apiUri(ApiUri.GET_HELPS);
        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public HelpDetailResponse getHelpDetail(int helpId) throws BhException {
        GatewayProxyRequest<HelpDetailResponse> req =
                new GatewayProxyRequest<HelpDetailResponse>().responseType(HelpDetailResponse.class)
                        .apiUri(ApiUri.GET_HELP_DETAIL).uriVariable("helpId", helpId);
        return httpGatewayTemplate.invoke(req);
    }
}
