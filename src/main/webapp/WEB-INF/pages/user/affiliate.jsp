<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>推荐中心</title>
    <style>
    .send-msg-btn {
        line-height: 30px;
        font-size: 15px;
        background-color: #FDFDFD;
        border: 1px solid #D7D7D7;
        text-align: center;
    }
    </style>

    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<!-- header start -->
<c:set var="header_name" value="推荐中心" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- start -->
<div style="width:100%; max-width:640px; min-width:320px; margin:0 auto; padding-top:10px; text-align: center;">
    <input type="hidden" value="${recommendUrl}" id="recommendUrl">
    <div>
        <c:choose>
            <c:when test="${!empty recommendUrl}">分享您的推荐码获取奖励</c:when>
            <c:otherwise>扫码关注唯宝汇微商城，开启购物新生活</c:otherwise>
        </c:choose>
    </div>
    <div>
        <img width="320" src="${recommendQRcode}">
    </div>
    <div style="padding-bottom:10px;">
        <span>长按二维码图片自动识别</span>
    </div>
    <c:if test="${!empty recommendUrl}">
    <div style="padding-bottom:10px;">
        <span><input type="submit" class="send-msg-btn" value="分享二维码" onclick="WeiXinShareBtn()"></span>
    </div>
    </c:if>
</div>
<script>
function WeiXinShareBtn() {
    alert("点击菜单中的“复制链接”，然后发给你的朋友");
}
</script>
<c:if test="${!empty recommendUrl}">
<script>
wx.config({
    appId: '${jsSignature.appId}',
    timestamp: '${jsSignature.timestamp}',
    nonceStr: '${jsSignature.nonceStr}',
    signature: '${jsSignature.signature}',
    jsApiList: [
                'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'onMenuShareQZone'
    ]
});
wx.ready(function(){
    var shareObj = {
            title : '唯宝汇微商城',
            desc : '中国最专业的珠宝奢侈品商城，买珠宝就上唯宝汇',
            link : '${recommendUrl}',
            imgUrl : '${doamin}logo.png',
            fail : function(res) {
                alert('分享失败');
            }
        };
    //分享到朋友圈
    wx.onMenuShareTimeline(shareObj);
    //分享给朋友
    wx.onMenuShareAppMessage(shareObj);
    //分享到QQ
    wx.onMenuShareQQ(shareObj);
});
</script>
</c:if>
<!-- end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->

