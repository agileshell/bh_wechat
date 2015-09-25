<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
<title>我的账户</title>
<style>
.head-img {
	height: 160px;
	width: 100%;
	background: url(resources/img/headbg.png);
	background-size: cover;
	text-align: center;
	color: #FFF;
}

.head-img .my-img {
	width: 75px;
	height: 75px;
	border-radius: 75px;
	border: 2px solid #FFF;
	box-shadow: inset 0 1px 8px rgba(0, 0, 0, 0.2);
	margin-top: 5px;
	margin-bottom: 5px;
	display: inline-block;
	overflow: hidden;
	background-size: cover;
}

.head-img p {
	font-size: 15px;
	line-height: 15px;
}

.padding-list {
	width: 100%;
	overflow: hidden;
	background-color: #FFF;
	box-shadow: 0 1px rgba(200, 200, 200, 0.6);
}

.padding-list li {
	float: left;
    width: 50%;
	height: 60px;
	vertical-align: middle;
	box-shadow: 1px 0 0 rgba(200, 200, 200, 0.6);
	text-align: center;
	display: table;
	position: relative;
}

.padding-list li a {
	color: #a5a5a5;
	font-size: 13px;
	text-decoration: none;
	display: table-cell;
	width: 100%;
	height: 100%;
	vertical-align: middle;
}

.padding-list li:last-child {
	box-shadow: none;
}

.padding-list li p {
	line-height: 10px;
	color: #848689;
}

.menu-list {
	width: 100%;
	margin-top: 10px;
	background-color: #FFF;
	overflow: hidden;
    box-shadow: 0 1px rgba(200, 200, 200, 0.6);
}

.menu-list li {
	float: left;
	width: 33.33%;
	text-align: center;
}

.menu-list li a {
	display: block;
	width: 100%;
	text-decoration: none;
}

.menu-list li a img {
	width: 40px;
	height: 40px;
	display: block;
	margin: 0 auto;
}

.menu-list li p {
	font-size: 10px;
	color: #6a6a6a;
	text-align: center;
	line-height: 10px;
}

.op-list {
    width: 100%;
}
.op-list li {
    position: relative;
    padding: 8px 0 8px 15px;
    box-shadow: 0 1px rgba(200, 200, 200, 0.6);
}
.op-list li span {
  font-size: 15px;
  color: #6a6a6a;
  line-height: 35px;
}
.op-list .arrow {
  position: absolute;
  right: 15px;
  top: 0;
  width: 10px;
  height: 100%;
  background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAOCAMAAAAhfX2ZAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAA8UExURQAAALi4uLOzs7m5ubm5ubW1tf7+/v////7+/v///7e3t7a2tre3t7CwsNTU1Pr6+pOTk319fXh4eHx8fOwzq/MAAAASdFJOUwCW/MD26icVBkit28+onEjV/BglC0QAAABPSURBVAjXbc1REoAgCARQ1BRSy1ruf9cYsemn/XrDLEBEddvJE4DlkqG/bp+5qyIO92U+J+OtqDJl3SR+WNHZIIep8VxJQGZ/LCEXerNmDx61A4IihAIWAAAAAElFTkSuQmCC) center right no-repeat;
}
</style>
</head>

<!-- header start -->
<c:set var="header_name" value="我的账户" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div class="common-wrapper">
    <div class="head-img">
        <span class="my-img" style="background-image: url('resources/img/default_avatar.png')"></span>
        <p>${profile.userName}</p>
        <!-- <p>银牌用户</p> -->
    </div>

    <ul class="padding-list">
        <li>
            <a href="bhPoints">
                <p>${profile.bhPoints}</p>
                <p>宝汇币</p>
            </a>
        </li>
        <li>
            <a href="dzPoints">
                <p>${profile.dzPoints}</p>
                <p>宝豆</p>
            </a>
        </li>
    </ul>
    <ul class="menu-list">
        <li>
            <a href="orders?status=all">
                <img src="resources/img/order_status_1.png">
                <p>全部订单</p>
            </a>
        </li>
        <li>
            <a href="orders?status=Notred">
                <img src="resources/img/order_status_2.png">
                <p>待付款</p>
            </a>
        </li>
        <li>
            <a href="orders?status=Delivers">
                <img src="resources/img/order_status_3.png">
                <p>配送中</p>
            </a>
        </li>
    </ul>
    <ul class="op-list">
        <li onclick="javascript:document.getElementById('cartBtn').click();">
            <a id="cartBtn" href="myCart">
                <span class="msg">我的购物车</span>
                <span class="arrow"></span>
            </a>
        </li>
        <li onclick="javascript:document.getElementById('addressBtn').click();">
            <a id="addressBtn" href="addresses">
                <span class="msg">收货地址管理</span>
                <span class="arrow"></span>
            </a>
        </li>
        <li onclick="javascript:document.getElementById('changePwdBtn').click();">
            <a id="changePwdBtn" href="changePassword">
                <span class="msg">修改登录密码</span>
                <span class="arrow"></span>
            </a>
        </li>
        <li onclick="javascript:document.getElementById('changePayPwdBtn').click();">
            <a id="changePayPwdBtn" href="changePayPassword">
                <span class="msg">修改支付密码</span>
                <span class="arrow"></span>
            </a>
        </li>
        <li onclick="javascript:document.getElementById('messageBtn').click();">
            <a id="messageBtn" href="messages">
                <span class="msg">消息中心</span>
                <span class="arrow"></span>
            </a>
        </li>
        <li onclick="javascript:document.getElementById('affiliateBtn').click();">
            <a id="affiliateBtn" href="affiliate?userId=${profile.userId}">
                <span class="msg">推荐中心</span>
                <span class="arrow"></span>
            </a>
        </li>
    </ul>
</div>

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
