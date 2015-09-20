package com.bh.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bh.wechat.service.AccountService;
import com.bh.wechat.service.WeChatService;
import com.bh.wechat.wx.constant.ConstantWeChat;
import com.bh.wechat.wx.model.message.resp.Article;
import com.bh.wechat.wx.model.message.resp.NewsMessage;
import com.bh.wechat.wx.model.message.resp.TextMessage;
import com.bh.wechat.wx.service.WechatMessageService;
import com.bh.wechat.wx.util.MessageUtil;

@Service
public class WeChatServiceImpl implements WeChatService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected AccountService accountService;

    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get("MsgType");

            TextMessage textMessage = (TextMessage) WechatMessageService.bulidBaseMessage(requestMap,
                    ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
            NewsMessage newsMessage = (NewsMessage) WechatMessageService.bulidBaseMessage(requestMap,
                    ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);

            String openid = requestMap.get("FromUserName");

            String respContent = "";
            if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_TEXT)) {// 处理文本信息
                // 文本消息
                String content = requestMap.get("Content");

                if ("tw".equals(content)) {
                    List<Article> articleList = new ArrayList<Article>();
                    Article article = new Article();
                    article.setTitle("测试TITLE");
                    article.setDescription("测试Description");
                    article.setPicUrl("图片地址");
                    article.setUrl("http://m.baidu.com");
                    articleList.add(article);
                    // 设置图文消息个数
                    newsMessage.setArticleCount(articleList.size());
                    // 设置图文消息包含的图文集合
                    newsMessage.setArticles(articleList);
                    // 将图文消息对象转换成xml字符串
                    respMessage = WechatMessageService.bulidSendMessage(newsMessage,
                            ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
                } else if ("cwb".equals(content)) {
                    textMessage.setContent("这就是纯文本");
                    respMessage = WechatMessageService.bulidSendMessage(textMessage,
                            ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
                }
            } else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_EVENT)) {// 处理事件信息
                // 事件类型
                String eventType = requestMap.get("Event");

                if (eventType.equals(ConstantWeChat.EVENT_TYPE_SUBSCRIBE)) {
                    // 关注
                    respContent = "感谢您的关注，祝您购物愉快！\n";

                    int userId = 0;
                    String eventKey = requestMap.get("EventKey");
                    if (StringUtils.isNoneBlank(eventKey)) {
                        Pattern pattern = Pattern.compile("qrscene_");
                        String[] strs = pattern.split(eventKey);
                        if (strs.length >= 2) {
                            userId = Integer.valueOf(strs[1]);
                        }
                    }

                    try {
                        accountService.followWechat(openid, userId);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                } else if (eventType.equals(ConstantWeChat.EVENT_TYPE_UNSUBSCRIBE)) {
                    // 取消关注
                } else if (eventType.equals(ConstantWeChat.EVENT_TYPE_SCAN)) {
                    int userId = 0;
                    // 二维码扫描事件
                    String eventKey = requestMap.get("EventKey");
                    if (StringUtils.isNoneBlank(eventKey)) {
                        Pattern pattern = Pattern.compile("qrscene_");
                        String[] strs = pattern.split(eventKey);
                        if (strs.length >= 2) {
                            userId = Integer.valueOf(strs[1]);
                        }
                    }

                    try {
                        accountService.followWechat(openid, userId);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }

                    respContent = "感谢您的关注，祝您购物愉快！\n";

                } else if (eventType.equals(ConstantWeChat.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");
                    // 自定义菜单点击事件
                    if (eventKey.equals("11")) {
                        respContent = "点击事件(key=11)";
                    }
                }
                textMessage.setContent(respContent);
                respMessage = WechatMessageService.bulidSendMessage(textMessage, ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return respMessage;
    }
}
