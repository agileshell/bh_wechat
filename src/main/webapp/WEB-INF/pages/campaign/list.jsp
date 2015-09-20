<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>最新活动</title>
</head>

<!-- header start -->
<c:set var="header_name" value="最新活动" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- list start -->
<div class="list p-sort radius">
    <div class="mc">
      <ul>
        <c:forEach items="${campaigns}" var="campaign">
          <a href="campaign/${campaign.campaignId}">
            <li class="first">
                <strong class="name1">${campaign.title}</strong>
                <span class="menu-botton-arrow"></span>
            </li>
          </a>
        </c:forEach>
      </ul>
    </div>
</div>
<!-- list end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
