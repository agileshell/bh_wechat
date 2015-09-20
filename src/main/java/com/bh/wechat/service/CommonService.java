package com.bh.wechat.service;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.response.CampaignDetailResponse;
import com.bh.wechat.response.CampaignListResponse;
import com.bh.wechat.response.HelpDetailResponse;
import com.bh.wechat.response.HelpListResponse;

@Service
public interface CommonService {

    CampaignListResponse listCampaigns() throws BhException;

    CampaignDetailResponse getCampaignDetail(int campaignId) throws BhException;

    HelpListResponse listHelps() throws BhException;

    HelpDetailResponse getHelpDetail(int helpId) throws BhException;

}
