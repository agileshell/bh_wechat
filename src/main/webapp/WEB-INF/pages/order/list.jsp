<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../config.jsp"%>
<head>
    <title>我的订单</title>
    <link rel="stylesheet" type="text/css" href="resources/css/order.css?v=${version}" />
    <script type="text/javascript" src="resources/js/template.js?v=${version}"></script>
</head>

<!-- header start -->
<c:set var="header_name" value="我的订单" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="profile" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- orders start -->
<div class="wrap">
    <input type="hidden" id="status" value="${requestData.status}">
    <input type="hidden" id="current_page" value="1">
    <section class="order-con">
        <ul class="order-list" id="orders_list">
        <c:if test="${fn:length(orders) == 0}">
            <li style="border:0;" class="empty-list-message">还没有订单信息，快去选购吧</li>
        </c:if>
          <c:forEach items="${orders}" var="order">
            <li>
                <div class="order-box">
                    <a class="new-mu_l2a new-p-re" href="order/${order.orderId}">
                        <div class="order-msg">
                            <img src="${order.product.coverImg}">
                            <div class="order-msg">
                                <p class="title">${order.product.name}</p>
                                <p class="price">¥${order.totalPrice}</p>
                                <p class="order-data">
                                    <c:choose>
                                        <c:when test="${order.status == 'Notred'}">未确认</c:when>
                                        <c:when test="${order.status == 'Paid'}">已付款 </c:when>
                                        <c:when test="${order.status == 'Stocking'}">已备货</c:when>
                                        <c:when test="${order.status == 'Delivers'}">已发货</c:when>
                                        <c:when test="${order.status == 'Receipt'}">已收货 </c:when>
                                    </c:choose>
                                </p>
                                <p class="order-data">${order.createdTime}</p>
                            </div>
                        </div>
                    </a>
                    <c:if test="${order.status == 'Notred'}">
                        <div class="pay-button">
                            <a class="pay-order" href="javascript:void(0);" onclick="javascript:payOrder(${order.orderId});">立即支付</a>
                            <a class="cancel-order" href="javascript:void(0);" onclick="javascript:cancelOrder(${order.orderId});">取消订单</a>
                        </div>
                    </c:if>
                </div>
            </li>
          </c:forEach>
        </ul>
        <c:if test="${fn:length(orders) != 0}">
            <div class="load-more" style="text-align:center;" id="fetchMore">
                <span style="font-size:12px;" id="fetchMoreMsg">加载更多</span>
            </div>
        </c:if>
    </section>
</div>

<div class="pop-total" id="pop-choose" style="display: none;">
    <div class="pop-bg"></div>
    <div class="pop pop2">
        <p class="pop-msg"></p>
        <div class="pop-btns">
            <a href="javascript:void(0)" onclick="$('#pop-choose').hide();" class="btn-pop btn-cancel">取消</a>
            <a href="javascript:;" class="btn-pop btn-continue">继续</a>
        </div>
    </div>
</div>
<script id="order-template" type="text/html">
    {{each list}}
        <li>
            <div class="order-box">
                <a class="new-mu_l2a new-p-re" href="order/{{$value.orderId}}">
                    <div class="order-msg">
                        <img src="={{$value.product.coverImg}}">
                        <div class="order-msg">
                            <p class="title">{{$value.product.name}}</p>
                            <p class="price">¥{{$value.totalPrice}}</p>
                            <p class="order-data">{{$value.status}}</p>
                            <p class="order-data">{{$value.createdTime}}</p>
                        </div>
                    </div>
                </a>
                {{if $value.status == 'Notred' }}
                    <div class="pay-order">
                        <a href="javascript:void(0);" onclick="javascript:cancelOrder({{$value.orderId}});">取消订单</a>
                    </div>
                    &nbsp;
                    <div class="pay-order">
                        <a href="javascript:void(0);" onclick="javascript:payOrder({{$value.orderId}});">立即支付</a>
                    </div>
                {{/if}}
            </div>
        </li>
    {{/each}}
</script>
<script type="text/javascript">
    $(function() {
        $("#fetchMore").bind("click", fetchMoreItem);
    });

    function cancelOrder(orderId) {
        if (confirm("确定要取消该订单吗？")) {
            $.ajax({
                url : 'api/cancelOrder',
                type : 'POST',
                data : {
                    orderId : orderId
                },
                dataType : 'json',
                success : function(rdata) {
                    if (rdata.ret == 0) {
                        window.location.href = 'orders?status=all';
                    } else {
                        switch (rdata.ret) {
                            case 1000:
                            case 3100:
                            case 3101:
                                alert(rdata.msg);
                                break;
                            case 1001:
                                pop({
                                    msg : '您的登录凭证已过期，请重新登录',
                                    btn : '登录',
                                    url : 'login'
                                });
                                break;
                            default:
                                alert(rdata.msg);
                                break;
                        }
                    }
                },
                error : function() {
                    alert("取消该订单失败");
                }
            });
        }
    }

    function payOrder(orderId) {
        window.location.href = 'gopay/' + orderId;
    }

    var fetchMoreItem = function() {
        var status = $("#status").val(),
        limit = 10,
        current_page = $('#current_page').val(),
        offset = current_page * limit;

        $.ajax({
            url : 'api/orders',
            type : 'GET',
            data: {status: status, offset: offset, limit: limit},
            dataType : 'json',
            beforeSend : function() {
                $('#fetchMoreMsg').html('加载中');
                $('#fetchMore').unbind("click");
            },
            success : function(data) {
                if (data.ret == 0) {
                    if(null != data.list && data.list.length > 0) {
                        var html = template('order-template', data);
                        $('#orders_list').append(html);
                        $('#current_page').val(++current_page);
                        $('#fetchMoreMsg').html('加载更多');
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
<!-- orders end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
