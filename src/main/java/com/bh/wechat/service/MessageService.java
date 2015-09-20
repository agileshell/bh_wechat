package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.response.MessageDetailResponse;
import com.bh.wechat.response.MessageListResponse;

public interface MessageService {

    MessageListResponse listMessages(String token) throws BhException;

    MessageDetailResponse getMessageDetail(int messageId, String token) throws BhException;
}
