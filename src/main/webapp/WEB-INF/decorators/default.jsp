<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:url value="<%=basePath%>" var="basePath" />
<c:set var="version" value="1.2.2" />

<!DOCTYPE html>
<!--[if IEMobile 7 ]>    <html class="no-js iem7"> <![endif]-->
<!--[if (gt IEMobile 7)|!(IEMobile)]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
    <base href="<%=basePath%>" />
    <title>唯宝汇微商城 - <decorator:title /></title>
    <meta charset="UTF-8" />
    <meta name="HandheldFriendly" content="True" />
    <meta name="MobileOptimized" content="320" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone = no"/>
    <meta name="format-detection" content="address=no;email=no"/>
    <meta http-equiv="cleartype" content="on" />
    <meta http-equiv="cache-control" content="no-cache">

    <meta name="description" content="唯宝汇微商城 - 亚洲最大、最安全的网上珠宝首饰奢侈品交易平台，提供各类高档珠宝首饰、服饰、美容、家居、数码、… 8亿优质特价商品，提供在线支付、先行赔付、假一赔三、七天无理由退换货、珠宝免费维修等安全交易保障服务，让你全面安心享受网上购物乐趣！" />
    <meta name="keywords" content="中国最专业的珠宝奢侈品商城-帮扶中国好企业,捧红中国好品牌-百业联盟,千店连锁,万品消费" />

    <meta name="apple-touch-fullscreen" content="yes"/>
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="apple-mobile-web-app-title" content="唯宝汇微商城" />

    <!-- This script prevents links from opening in Mobile Safari. https://gist.github.com/1042026 -->
    <script>(function(a,b,c){if(c in b&&b[c]){var d,e=a.location,f=/^(a|html)$/i;a.addEventListener("click",function(a){d=a.target;while(!f.test(d.nodeName))d=d.parentNode;"href"in d&&(d.href.indexOf("http")||~d.href.indexOf(e.host))&&(a.preventDefault(),e.href=d.href)},!1)}})(document,window.navigator,"standalone")</script>

    <link rel="stylesheet" type="text/css" href="resources/css/main.css?v=${version}" />
    <script type="text/javascript" src="resources/js/jquery-1.11.1.min.js?v=${version}"></script>
    <script type="text/javascript" defer src="resources/js/util.js?v=${version}"></script>

    <link rel="Shortcut Icon" href="<%=basePath%>favicon.ico" />

    <decorator:head />

<script type="text/javascript">
    window.onpopstate = function() {
        var currentState = event.state;
        if (currentState == undefined || currentState == null) {
            window.history.replaceState('reloaded', null, document.location);
        } else {
            window.history.replaceState(null, null, document.location);
            window.location.reload();
        }
    };
</script>
</head>
<body>
    <!--[if lte IE 7]>
    <p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，本系统暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
      以获得更好的体验！</p>
    <![endif]-->
    <decorator:body />
</body>
</html>
