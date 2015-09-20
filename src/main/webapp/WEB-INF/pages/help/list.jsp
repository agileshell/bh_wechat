<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>帮助中心</title>
</head>

<!-- header start -->
<c:set var="header_name" value="帮助中心" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- list start -->
<div class="list p-sort radius">
    <div class="mc">
      <ul>
        <c:forEach items="${helps}" var="help">
          <a href="help/${help.helpId}">
            <li class="first">
                <strong class="name1">${help.title}</strong>
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
