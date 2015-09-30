<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../config.jsp"%>
<head>
    <title>宝豆</title>
    <link rel="stylesheet" type="text/css" href="resources/css/currency.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="宝豆" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="profile" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div class="bh-detail">
    <div>
        <img width="100" height="100" src="resources/img/currency.png">
    </div>
    <div style="padding-top: 10px;">
        <span style="display:block; font-size:20px; font-weight:500;">我的宝豆</span>
        <span style="display:block; padding-top:5px; font-size:30px; font-weight:bold;">${dzPoints}</span>
    </div>
    <div style="padding-top: 30px;">
        <input type="hidden" id="current_page" value="1"/>
        <ul class="op-list">
          <c:forEach items="${histories}" var="history">
            <li>
                <div class="div_left">
                    <span>${history.type}</span>
                    <span class="time">${history.dealTime}</span>
                </div>
                <div class="div_right">
                    <span>${history.amount}</span>
                </div>
            </li>
          </c:forEach>
        </ul>
        <c:if test="${fn:length(histories) > 0}">
            <div class="load-more" id="fetchMore" style="text-align:center; clear: both; border: 1px solid #f2f2f2;">
                <span style="font-size:12px;" id="fetchMoreMsg">加载更多</span>
            </div>
        </c:if>
    </div>
</div>
<script id="history-template" type="text/html">
    {{each list}}
        <li>
            <div class="div_left">
                <span>{{$value.history.type}}</span>
                <span class="time">{{$value.dealTime}}</span>
            </div>
            <div class="div_right">
                <span>{{$value.amount}}</span>
            </div>
        </li>
    {{/each}}
</script>
<script>
    $(function() {
        $("#fetchMore").bind("click", fetchMoreItem);
    });

    var fetchMoreItem = function() {
        var limit = 10,
        current_page = $('#current_page').val(),
        offset = current_page * limit;

        $.ajax({
            url : 'api/deal/history/dzPoints',
            type : 'GET',
            data: {offset: offset, limit: limit},
            dataType : 'json',
            beforeSend : function() {
                $('#fetchMoreMsg').html('加载中');
                $('#fetchMore').unbind("click");
            },
            success : function(data) {
                if (data.ret == 0) {
                    if(null != data.list && data.list.length > 0) {
                        var html = template('history-template', data);
                        $('.op-list').append(html);
                        $('#current_page').val(++current_page);
                        if (data.list.length < 10) {
                        	$('#fetchMoreMsg').html('没有了');
                        } else {
                        	$('#fetchMoreMsg').html('加载更多');
                        }
                    } else {
                        $('#fetchMoreMsg').html('没有了');
                    }
                    $("#fetchMore").bind("click", fetchMoreItem);
                } else {
                    $('#fetchMoreMsg').html('加载失败');
                    setTimeout(function(){
                        $('#fetchMoreMsg').html('加载更多'); 
                        $("#fetchMore").bind("click", fetchMoreItem);
                    }, 2000);
                }
            },
            error : function() {
                $('#fetchMore').html('加载失败');
                setTimeout(function(){
                    $('#fetchMoreMsg').html('加载更多'); 
                    $("#fetchMore").bind("click", fetchMoreItem);
                }, 2000);
            }
        });
    };
</script>

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->