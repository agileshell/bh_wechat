package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.GatewayProxyRequest;
import com.bh.wechat.response.MessageDetailResponse;
import com.bh.wechat.response.MessageListResponse;
import com.bh.wechat.service.MessageService;

@Service
public class MessageServiceImpl extends BaseService implements MessageService {

    @Override
    public MessageListResponse listMessages(String token) throws BhException {
        GatewayProxyRequest<MessageListResponse> req = new GatewayProxyRequest<MessageListResponse>()
                .responseType(MessageListResponse.class).apiUri(ApiUri.GET_MESSAGES).parameter("token", token);
        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public MessageDetailResponse getMessageDetail(int messageId, String token) throws BhException {
        GatewayProxyRequest<MessageDetailResponse> req = new GatewayProxyRequest<MessageDetailResponse>()
                .responseType(MessageDetailResponse.class).apiUri(ApiUri.GET_MESSAGE_DETAIL).parameter("token", token)
                .uriVariable("messageId", messageId);
        return httpGatewayTemplate.invoke(req);
    }

}
