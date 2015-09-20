package com.bh.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bh.wechat.request.WeChatRequest;
import com.bh.wechat.wx.service.WechatSignatureService;

@Controller
@RequestMapping("wechat")
public class WeChatController extends BaseController {

    /**
     * 校验信息是否是从微信服务器发过来的。
     * 
     * @param weChat
     * @param out
     */
    @RequestMapping(method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public void valid(WeChatRequest weChat, PrintWriter out) {
        String signature = weChat.getSignature(); // 微信加密签名
        String timestamp = weChat.getTimestamp(); // 时间戳
        String nonce = weChat.getNonce();// 随机数
        String echostr = weChat.getEchostr();// 随机字符串

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WechatSignatureService.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }

        out.flush();
        out.close();
    }

    /**
     * 微信消息的处理
     * 
     * @param request
     * @param out
     * @throws IOException
     */
    @RequestMapping(method = { RequestMethod.POST }, produces = "application/xml;charset=UTF-8")
    public void dispose(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {

        // 调用核心业务类接收消息、处理消息
        String respMessage = weChatService.processRequest(request);
        logger.info(respMessage);

        // 响应消息
        out.print(respMessage);
        out.flush();
        out.close();
    }

}