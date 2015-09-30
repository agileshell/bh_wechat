<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../config.jsp"%>
<head>
    <title>提现明细</title>
    <link rel="stylesheet" type="text/css" href="resources/css/currency.css?v=${version}" />
    <script type="text/javascript" src="resources/js/template.js?v=${version}"></script>
</head>

<!-- header start -->
<c:set var="header_name" value="提现明细" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="bhPoints" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->


<div class="bh-detail">
    <div>
        <input type="hidden" id="current_page" value="1"/>
        <ul class="op-list">
          <c:forEach items="${histories}" var="history">
            <li>
                <div class="div_left">
                    <span>手续费：￥<fmt:formatNumber value="${history.fee}" pattern="#0.00"/></span>
                    <span class="time">${history.dealTime}</span>
                </div>
                <div class="div_right">
                    <span><fmt:formatNumber value="${history.amount}" pattern="#0.00"/></span>
                    <span>${history.status == 'success' ? '成功' : history.status == 'fail' ? '失败' : '处理中'}</span>
                </div>
            </li>
          </c:forEach>
        </ul>
        <c:choose>
            <c:when test="${fn:length(histories) == 0}">
                <div class="load-more" style="text-align:center; clear: both; border: 1px solid #f2f2f2;">
                    <span style="font-size:12px;" id="fetchMoreMsg" >空空如也</span>
                </div>
            </c:when>
            <c:when test="${fn:length(histories) < 10}">
                <div class="load-more" style="text-align:center; clear: both; border: 1px solid #f2f2f2;">
                    <span style="font-size:12px;" id="fetchMoreMsg">没有了</span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="load-more" id="fetchMore" style="text-align:center; clear: both; border: 1px solid #f2f2f2;">
                    <span style="font-size:12px;" id="fetchMoreMsg">加载更多</span>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script id="history-template" type="text/html">
    {{each list}}
        <li>
            <div class="div_left">
                <span>手续费：￥{{$value.fee}}</span>
                <span class="time">{{$value.dealTime}}</span>
            </div>
            <div class="div_right">
                <span>{{$value.amount}}</span>
                {{if $value.status == 'success'}}
                    <span>成功</span>
                {{else}}
                    {{if $value.status == 'fail'}}
                    	<span>失败</span>
                	{{else}}
                    	<span>处理中</span>
                	{{/if}}
                {{/if}}
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
            url : 'api/bhPoints/withdraw/history',
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
                    	var sdata = data.list;
                    	var slist = [];
                    	for (var i = 0, j = sdata.length; i < j; i++) {
                    		slist[i] = sdata[i];
                    		slist[i].fee = slist[i].fee.toFixed(2);
                    		slist[i].amount = slist[i].amount.toFixed(2);
                    	}
                        var html = template('history-template', {'list': slist});
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