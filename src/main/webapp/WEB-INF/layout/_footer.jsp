<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<footer class="footer">
    <div class="m-1px-line-up"></div>
    <ul class="footer-links">
         <li><a rel="nofollow" href="home">首页</a></li>
        <c:choose>
            <c:when test="${empty sessionScope.openid}">
                <li><a rel="nofollow" href="login">登录</a></li>
            </c:when>
            <c:otherwise>
                <li><a rel="nofollow" href="profile">我的账户</a></li>
            </c:otherwise>
        </c:choose>
        <li><a rel="nofollow" href="helps">帮助</a></li>
        <li><a rel="nofollow" href="aboutus">关于</a></li>
    </ul>
    <div class="m-1px-line-up"></div>
    <div class="footer-copyright">Copyright © 2012-2015 唯宝汇vBaoHui.com 版权所有</div>
</footer>