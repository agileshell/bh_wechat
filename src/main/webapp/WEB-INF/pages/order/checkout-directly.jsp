<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../config.jsp"%>
<head>
    <title>填写订单</title>
    <link rel="stylesheet" type="text/css" href="resources/css/checkout.css?v=${version}" />
</head>

<script type="text/javascript">
    $('body').css('background', '#f0f2f5');
</script>
<style>
.txt-input {
    width: 80px;
    height: 30px;
    line-height: normal;
    padding: 5px;
    border: 1px solid #d7d7d7;
    border-radius: 3px;
    background: #fff;
    font-family: '\5fae\8f6f\96c5\9ed1';
    font-size: 16px;
    color: #252525;
    -webkit-appearance: none;
}
</style>

<!-- header start -->
<c:set var="header_name" value="填写订单" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="product/${sessionScope.product.productId}" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- order start -->
<div class="common-wrapper" style="position: inherit;">
    <input type="hidden" id="checkout_uuid" value="${sessionScope.checkout_uuid}"/>

    <input type="hidden" id="productId" value="${sessionScope.product.productId}"/>
    <input type="hidden" id="guige" value='{"color":"${sessionScope.guige.color}","size":"${sessionScope.guige.size}"}' />
    <input type="hidden" id="qty" value="${sessionScope.qty}"/>
    <input type="hidden" id="addressId" value="${sessionScope.addressId}"/>
    <input type="hidden" id="inventory" value="${sessionScope.product.inventory}"/>

    <input type="hidden" id="canUseBaodou" value="${sessionScope.canUseBaodou}"/>
    <input type="hidden" id="totalPrice" value="${sessionScope.totalPrice}"/>
    <input type="hidden" id="totalBhPoints" value="${sessionScope.totalBhPoints}"/>
    <input type="hidden" id="totalQianPoints" value="${sessionScope.totalQianPoints}"/>
    <input type="hidden" id="totalBaodou" value="${sessionScope.totalBaodou}"/>
    <div class="w checkout" style="padding: 0px;">
        <div class="step1 border-1px" onclick="javascript:document.getElementById('addressBtn').click();">
            <div class="m step1-in ">
                <%
                session.setAttribute("selectAddressRedirectView", "order/checkout-directly");
                %>
                <a id="addressBtn" href="order/addresses" class="s-href">
                  <c:choose>
                    <c:when test="${!empty sessionScope.addressId && !empty sessionScope.address}">
                        <div class="mt_new">
                            <div class="s1-name">
                                <i></i><span>${sessionScope.address.name}</span>
                            </div>
                            <div class="s1-phone">
                                <i></i>${sessionScope.address.mobile}
                            </div>
                        </div>
                        <div class="mc step1-in-con">${sessionScope.address.fullAddress}</div>
                    </c:when>
                    <c:otherwise>选择收货地址</c:otherwise>
                  </c:choose>
                </a>
            </div>
            <b class="s1-borderT"></b>
            <b class="s1-borderB"></b>
            <span class="s-point"></span>
        </div>
        <div class="step2 border-1px">
            <div class="m bdb-1px">
                <div class="mb">
                    <a href="javascript:;" id="paymentMethod1" class="btn3 btn3-ch" onclick="javascript:choosePayType('1');">网银在线</a>
                    <a href="javascript:;" id="paymentMethod2" class="btn3" onclick="javascript:choosePayType('2');">宝汇币</a>
                    <input type="hidden" id="paymentMethod" name="paymentMethod" value="1">
                </div>
            </div>
        </div>
        <div class="step3 border-1px">
            <a href="product/${sessionScope.product.productId}" class="s-href">
                <div class="s-item">
                    <div class="sitem-l">
                        <div class="sl-img">
                            <img src="${sessionScope.product.coverImg}">
                        </div>
                    </div>
                    <div class="sitem-m">
                        <p class="sitem-m-txt">${sessionScope.product.name}</p>
                        <p>×${sessionScope.qty}</p>
                        <c:if test="${!empty sessionScope.guige}">
                            <c:if test="${!empty sessionScope.guige.color}">
                                颜色：${sessionScope.guige.color}&nbsp;
                            </c:if>
                            <c:if test="${!empty sessionScope.guige.size}">
                                尺寸：${sessionScope.guige.size}
                            </c:if>
                        </c:if>
                    </div>
                    <div class="sitem-r">￥${sessionScope.product.discountPrice != 0 ? sessionScope.product.discountPrice : sessionScope.product.price}</div>
                    <span class="s-point"></span>
                </div>
            </a>
        </div>
        <div class="step4">
            <div class="s-item">
                <div class="sitem-m">
                    宝豆：<span>共${sessionScope.totalBaodou}宝豆，本单可使用${sessionScope.canUseBaodou}宝豆</span>
                    <br/>
                    <c:if test="${sessionScope.canUseBaodou > 0}">
                    <br/>
                    使用 <input type="text" id="baodou" name="baodou" value="0" class="txt-input"> 个宝豆
                    </c:if>
                    <c:if test="${sessionScope.canUseBaodou <= 0}">
                    <input type="hidden" id="baodou" name="baodou" value="0">
                    </c:if>
                    
                </div>
            </div>
        </div>
        <div class="step5 border-1px" style="margin-bottom: 5em;">
            <div class="s-item">
                <div class="sitem-l">商品金额</div>
                <div class="sitem-r">￥<fmt:formatNumber value="${sessionScope.productTotalPrice}" pattern="#0.00"/></div>
            </div>
            <div class="s-item">
                <div class="sitem-l">运费</div>
                <div class="sitem-r">￥${sessionScope.shipFee}</div>
            </div>
            <div class="s-item">
                <div class="sitem-l">宝汇币</div>
                <div id="pay-a" class="sitem-r">0</div>
            </div>
            <div class="s-item">
                <div class="sitem-l">宝豆</div>
                <div id="pay-c" class="sitem-r">0</div>
            </div>
        </div>
    </div>

    <div class="pay-bar" id="pay-bar">
        <div class="payb-con">
            实付款：￥<span id="payMoney"><fmt:formatNumber value="${sessionScope.totalPrice}" pattern="#0.00"/></span>
        </div>
        <a class="payb-btn" onclick="javascript:submitOrder();" href="javascript:void(0);"> 提交订单 </a>
    </div>
    <!-- 弹层 -->
    <div class="popup-w" style="display:none;"></div>
    <div id="inputPwdWindow" class="confirm-w" style="display:none;">
        <div class="confirm">
            <div class="confirm-txt">
                <div class="m pay-password">
                    <div>
                        <h3>安全验证</h3>
                    </div>
                    <div class="mc" style="margin-top:0!important;">
                        <p>您使用了宝汇币，为保证安全，请进行验证</p>
                        <p class="pp-red" id="erroTip"></p>
                        <div class="input-w">
                            <input type="password" id="payPassword" autocomplete="off" name="payPassword" placeholder="输入支付密码">
                        </div>
                        <p>支付密码默认为您的登录密码,您可以在个人中心进行修改</p>
                    </div>
                </div>
            </div>
            <div class="confirm-btn">
                <a href="javascript:;" onclick="closeInputPwdWindow()" class="btn2 ctn01">取消</a>
                <a href="javascript:;" onclick="virtualPaySubmit()" id="submiOrder" class="btn2 ctn02 pass-no pp-btn">确定使用</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var background = document.getElementById("background");
    var openPwdWindowHeight = $("#inputPwdWindow").height();
    function backScroll() {
        background.style.top = document.body.scrollTop + "px"
    }
    window.onscroll = backScroll;
    function openPwdLinkWindow() {
        $(".popup-w").css({
            height: $(document).height(),
            display: "block"
        });
        $("#inputPwdWindow").css({
            top: $(window).scrollTop() + $(window).height() / 2 - openPwdWindowHeight / 2,
            display: "block"
        })
    }
    function closeInputPwdWindow() {
        $(".popup-w").css({
            display: "none"
        });
        $("#inputPwdWindow").css({
            display: "none"
        })
    }
    function choosePayType(id) {
        $(".btn3").removeClass("btn3-ch");
        $("#paymentMethod" + id).addClass("btn3-ch");
        $("#paymentMethod").val(id);

        var baodou = parseInt($("#baodou").val());
        var needPay = parseFloat($("#totalPrice").val()) - baodou / 100, needPay = needPay.toFixed(2);
        var payMoney = $("#payMoney");
        if (id == 1) {
            payMoney.text(needPay);
            $("#pay-a").text(0);
        } else if (id == 2) {
            $("#pay-a").text(needPay);
            payMoney.text(0);
            $("#pay-b").text(0);
        } else {
            $("#pay-a").text(0);
            payMoney.text(0);
            $("#pay-b").text(needPay);
        }
    }

    $("#baodou").on("blur", function() {
        var baodou = parseInt($("#baodou").val(), 10);
        if (isNaN(baodou)) {
            baodou = 0;
        } else if (baodou < 0) {
            baodou = 0
        }
        $("#baodou").val(baodou);

        var canUseBaodou = parseInt($("#canUseBaodou").val());
        var totalBaodou = parseInt($("#totalBaodou").val());
        if (canUseBaodou > baodou) {
            if (totalBaodou <= baodou) {
                baodou = totalBaodou;
                $("#baodou").val(baodou);
                $("#pay-c").text(baodou);
            } else {
                $("#pay-c").text(baodou);
            }
        } else {
            if (totalBaodou > canUseBaodou) {
                baodou = canUseBaodou;
                $("#baodou").val(baodou);
                $("#pay-c").text(baodou);
            } else {
                 baodou = totalBaodou;
                 $("#baodou").val(baodou);
                 $("#pay-c").text(baodou);
            }
        }

        var needPay = parseFloat($("#totalPrice").val()) - baodou / 100, needPay = needPay.toFixed(2);
        if ($("#paymentMethod").val() == 1) {
            $("#pay-a").text(0);
            $("#payMoney").text(needPay);
        } else if ($("#paymentMethod").val() == 2) {
            $("#pay-a").text(needPay);
            $("#payMoney").text(0);
        }
    });

    $("#payPassword").on("input", function() {
        if (this.value.length > 0) {
            $("#submiOrder").removeClass("pass-no");
        } else {
            $("#submiOrder").addClass("pass-no");
        }
    });

    function submitOrder() {
        var addressId = $("#addressId").val();
        if (addressId == '' || addressId <= 0) {
            alert("选择一个收货地址");
            return;
        }

        var qty = $("#qty").val();
        if ($("#inventory").val() < qty) {
            alert("库存不足");
        }

        var baodou = parseInt($("#baodou").val());
        var needPay = parseFloat($("#totalPrice").val()), needPay = needPay.toFixed(2);
        if (baodou > 0) {
            needPay = needPay - baodou / 100;
        }

        var paymentMethod = $("#paymentMethod").val();
        if (paymentMethod == 2) {
            if (parseInt($("#totalBhPoints").val()) < needPay) {
                alert("宝汇币不足");
                return;
            }
        } else if (paymentMethod == 3) {
            if (parseInt($("#totalQianPoints").val()) < needPay) {
                alert("乾币不足");
                return;
            }
        }

        if (paymentMethod == 2) {
            openPwdLinkWindow();
        } else {
            virtualPaySubmit();
        }
    }

    function virtualPaySubmit() {
        var paymentMethod = $("#paymentMethod").val();
        if (paymentMethod != 2 || (paymentMethod == 2 && $("#payPassword").val().length > 0)) {
            var productId = $("#productId").val();
            var guige = $("#guige").val();
            var qty = $("#qty").val();
            var baodou = parseInt($("#baodou").val());
            var addressId = $("#addressId").val();
            var payPassword = $("#payPassword").val();
            $.ajax({
                url : 'api/order/directly',
                type : 'POST',
                data : {
                    productId : productId,
                    guige : guige,
                    qty : qty,
                    baodou : baodou,
                    addressId : addressId,
                    paymentMethod: paymentMethod,
                    payPassword: payPassword,
                    checkoutUuid: $("#checkout_uuid").val()
                },
                dataType : 'json',
                success : function(rdata) {
                    if (rdata.ret == 0) {
                        window.location.href = 'gopay/' + rdata.orderId;
                    } else {
                        switch (rdata.ret) {
                            case 1000:
                            case 3000:
                            case 3001:
                            case 3002:
                            case 3003:
                            case 3004:
                            case 3005:
                                alert(rdata.msg);
                                break;
                            case 1500:
                                alert("此订单已提交，请勿重复提交");
                            default:
                                alert(rdata.msg);
                                break;
                        }
                    }
                },
                error : function() {
                    alert("提交订单失败");
                }
            });
        }
    }
</script>
<!-- order end -->