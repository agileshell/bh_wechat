<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>${campaign.title}</title>
    <script type="text/javascript" src="resources/js/m.scale.js?v=${version}"></script>
    <style>
    .detail {
        position: relative;
        padding: 5px 0 0 15px;
        font-size: .75em;
        color: #5a5a5a;
    }
    .scale-box {
        display: table;
    }
    .scale-box img {
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    </style>
</head>

<!-- header start -->
<c:set var="header_name" value="活动详情" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- description start -->
<c:if test="${campaign.ret != 0}">
    <c:redirect url="/error">
        <c:param name="errorMessage" value="${campaign.msg}"/> 
    </c:redirect>
</c:if>
<div class="detail" id="wareInfo">
    <div id="scale-parent">
        <div class="scale-box" id="scale-cont">
            <!-- content start -->
            ${campaign.content}
            <!-- content end -->
        </div>
    </div>
</div>
<script type="text/javascript">
scale.ready(function(){
    scale.init();
});
</script>
<!-- description end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
