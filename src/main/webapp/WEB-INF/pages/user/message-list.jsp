<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../config.jsp"%>
<head>
    <title>消息中心</title>
</head>

<!-- header start -->
<c:set var="header_name" value="消息中心" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- list start -->
<div class="list p-sort radius">
    <div class="mc">
    <c:choose>
        <c:when test="${fn:length(messages) == 0}">
            <div style="text-align: center;">
                无任何消息
            </div>
        </c:when>
        <c:otherwise>
         <ul>
            <c:forEach items="${messages}" var="message">
              <a href="message/${message.messageId}">
                <li class="first">
                    <strong class="name1">${message.title}</strong>
                    <span class="menu-botton-arrow"></span>
                </li>
              </a>
            </c:forEach>
          </ul>
        </c:otherwise>
    </c:choose>
    </div>
</div>
<!-- list end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
