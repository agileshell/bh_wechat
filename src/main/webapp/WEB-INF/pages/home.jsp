<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="config.jsp"%>
<head>
    <title>中国最专业的珠宝奢侈品商城</title>
    <link rel="stylesheet" type="text/css" href="resources/css/home.css?v=${version}" />
    <script type="text/javascript" src="resources/js/banner.js?v=${version}"></script>

    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<script type="text/javascript">
    $('body').css('background', '#d5d5d6');
</script>
<div class="new-ct main">
    <input type="hidden" value="${fn:length(campaigns)}" id="activity" />
    <div class="banner scroll-wrapper" id="idContainer2" ontouchstart="touchStart(event)" ontouchmove="touchMove(event);" ontouchend="touchEnd(event);">
        <ul class="scroller" style="position: relative; left: 0px; width: ${fn:length(campaigns)}00%" id="idSlider2">
            <c:forEach items="${campaigns}" var="campaign">
            <li style="width: -100.0%">
                <a href='campaign/${campaign.campaignId}'> 
                    <img src="${campaign.coverImg}" alt="${campaign.title}" />
                </a>
            </li>
          </c:forEach>
        </ul>
        <ul class="new-banner-num new-tbl-type" id="idNum">
        </ul>
    </div>

    <div class="logo">
        <img src="resources/img/index_logo.jpg" alt="logo">
    </div>

    <div class="search" style="position: relative">
        <span class="input-box">
            <form action="products" id="searchForm">
                <input name="keyword" type="text" id="newkeyword" class="new-input" required="" value="">
            </form>
            <a href="javascript:void(0)" onclick="$('#searchForm').submit();" class="btn-search">
                <span>search</span>
            </a>
        </span>
    </div>

    <div class="category">
        <ul class="cate-menu tbl-type" id="categoryMenu">
            <li class="tbl-cell route1">
                <a href="products" class="menu1">
                    <span class="menu1-icon"><span></span></span> <span class="cate-name">新品</span>
                </a>
            </li>
            <li class="tbl-cell route2">
                <a href="myCart" class="menu2">
                    <span class="menu2-icon"><span></span></span> <span class="cate-name">购物车</span>
                </a>
            </li>
            <li class="tbl-cell route3">
                <a href="profile" class="menu3">
                    <span class="menu3-icon"><span></span></span> <span class="cate-name">我的</span>
                </a>
            </li>
            <li class="tbl-cell route4">
                <a href="campaigns" class="menu4">
                    <span class="menu4-icon"><span></span></span> <span class="cate-name">活动</span>
                </a>
            </li>
        </ul>
    </div>

    <div class="ad tbl-type">
        <c:choose>
          <c:when test="${fn:length(products) == 0}">
            <div class="tbl-cell">
                <a href="javascript:void(0);" class="ad-border">
                    <img src="resources/img/home/shemisl.jpg" height="175">
                </a>
                <a href="javascript:void(0);">
                    <img src="resources/img/home/ylili.jpg" height="175">
                </a>
            </div>
          </c:when>
          <c:otherwise>
            <div class="tbl-cell">
             <c:forEach items="${products}" var="product">
                <a href="product/${product.productId}" class="ad-border">
                    <img src="${product.coverImg}" height="175" alt="${product.name}">
                </a>
             </c:forEach>
            </div>
          </c:otherwise>
        </c:choose>
        <div class="tbl-cell">
            <div class="ad-left">
                <a href="products?keyword=翡翠" class="ad-border">
                    <img src="resources/img/home/1.jpg" height="70" alt="翡翠专场">
                </a> 
                <a href="products?keyword=尼诺里拉" class="ad-border">
                    <img src="resources/img/home/2.jpg" height="70" alt="尼诺里拉">
                </a> 
                <a href="products?keyword=饰琴画衣" class="ad-border">
                    <img src="resources/img/home/3.jpg" height="70" alt="飾琴书画">
                </a>
                <a href="products?keyword=崔德毅" class="ad-border">
                    <img src="resources/img/home/4.jpg" height="70" alt="崔德毅书法">
                </a>
                <a href="products?keyword=诺丽" class="ad-border">
                    <img src="resources/img/home/5.jpg" height="70" alt="诺利康泰">
                </a>
            </div>
        </div>
    </div>
</div>

<!-- footer start -->
<div class="login-area">
    <%@ include file="../layout/_footer.jsp"%>
</div>
<!-- footer end -->

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

<script>
    $(document).ready(function() {
        $("#categoryMenu li").addClass("route");
    })
    //活动轮播图
    var picCount = $("#activity").val();
    $(".pic-num1").css("width", (picCount * 30) + "px");
    var forEach = function(array, callback) {
        for (var i = 0, len = array.length; i < len; i++) {
            callback.call(this, array[i], i);
        }
    }
    var st = createPicMove("idContainer2", "idSlider2", picCount); //图片数量更改后需更改此数值
    var nums = [];
    //插入数字
    for (var i = 0, n = st._count - 1; i <= n; i++) {
        var li = document.createElement("li");
        nums[i] = document.getElementById("idNum").appendChild(li);
    }
    //设置按钮样式
    st.onStart = function() {
        //forEach(nums, function(o, i){ o.className = ""})
        forEach(nums, function(o, i) {
            o.className = st.Index == i ? "new-tbl-cell on" : "new-tbl-cell";
        })
    }
    // 重新设置浮动
    $("#idSlider2").css("position", "relative");
    var _initX = 0;
    var _finishX = 0;
    var _startX = 0;
    var _startY = 0;

    function touchStart(event) {
        _startX = event.touches[0].clientX;
        _startY = event.touches[0].clientY;
        _initX = _startX;
    }

    function touchMove(event) {
        var touches = event.touches;
        var _endX = event.touches[0].clientX;
        var _endY = event.touches[0].clientY;
        if (Math.abs(_endY - _startY) > Math.abs(_endX - _startX)) {
            return;
        }
        event.preventDefault();
        _finishX = _endX;
        var _absX = Math.abs(_endX - _startX);
        var lastX = $('#idSlider2').css('left').replace('px', '');
        if (_startX > _endX) {
            st.Stop();
            $('#idSlider2').css('left', (parseInt(lastX) - _absX) + 'px');
        } else {
            st.Stop();
            $('#idSlider2').css('left', (parseInt(lastX) + _absX) + 'px');
        }
        _startX = _endX;
    }
    //触屏  离开屏幕事件

    function touchEnd(event) {
        if (_finishX == 0) {
            return;
        }
        if (_initX > _finishX) {
            bindEvent(_initX, _finishX);
        } else if (_initX < _finishX) {
            bindEvent(_initX, _finishX);
        }
        _initX = 0;
        _finishX = 0;
    }

    function bindEvent(start, end) {
        if (start >= end) {
            st.Next();
        } else {
            st.Previous();
        }
    }
    st.Run();
    var resetScrollEle = function() {
        var slider2Li = $("#idSlider2 li");
        slider2Li.css("width", $(".scroll-wrapper").width() + "px");
    }
    window.addEventListener("resize", function() {
        st.Change = st._slider.offsetWidth / st._count;
        st.Next();
        resetScrollEle();
    });
    window.addEventListener("orientationchange", function() {
        st.Change = st._slider.offsetWidth / st._count;
        st.Next();
        resetScrollEle();
    })
    resetScrollEle();
    $("#newkeyword").focus(function() {
        setTimeout(function() {
            window.scrollTo(0, $("#newkeyword").offset().top - 60);
        }, 10);
    });
</script>
