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

<!-- header start -->
<c:set var="header_name" value="填写订单" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="myCart" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- order start -->
<div class="common-wrapper" style="position: inherit;">
    <input type="hidden" id="checkout_uuid" value="${sessionScope.checkout_uuid}"/>

    <input type="hidden" id="cartProductIds" value="${sessionScope.cartProductIds}"/>
    <input type="hidden" id="addressId" value="${sessionScope.addressId}"/>

    <input type="hidden" id="totalPrice" value="${sessionScope.totalPrice}"/>
    <input type="hidden" id="totalBhPoints" value="${sessionScope.totalBhPoints}"/>
    <input type="hidden" id="totalQianPoints" value="${sessionScope.totalQianPoints}"/>
    <input type="hidden" id="totalBaodou" value="${sessionScope.totalBaodou}"/>
    <div class="w checkout" style="padding: 0px;">
        <div class="step1 border-1px" onclick="javascript:document.getElementById('addressBtn').click();">
            <div class="m step1-in ">
                <%
                session.setAttribute("selectAddressRedirectView", "order/checkout");
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
        <c:forEach items="${cartProducts}" var="cartProduct">
        <div class="step3 border-1px">
            <a href="product/${cartProduct.productId}" class="s-href">
                <div class="s-item">
                    <div class="sitem-l">
                        <div class="sl-img">
                            <img src="${cartProduct.coverImg}">
                        </div>
                    </div>
                    <div class="sitem-m">
                        <p class="sitem-m-txt">${cartProduct.name}</p>
                        <p>X${cartProduct.qty}</p>
                        <c:if test="${!empty cartProduct.guige}">
                            <p>${cartProduct.guige}</p>
                        </c:if>
                    </div>
                    <div class="sitem-r">￥${cartProduct.price}</div>
                    <span class="s-point"></span>
                </div>
            </a>
        </div>
        </c:forEach>
        <div class="step4">
            <div class="s-item">
                <div class="sitem-m">
                    宝豆：<span>共${sessionScope.totalBaodou}宝豆，本单可使用${sessionScope.canUseBaodou}宝豆</span>
                    <input type="hidden" id="baodou" name="baodou" value="0">
                </div>
            </div>
        </div>
        <div class="step5 border-1px">
            <div class="s-item">
                <div class="sitem-l">商品金额</div>
                <div class="sitem-r">￥<fmt:formatNumber value="${sessionScope.totalPrice}" pattern="#0.00"/>元</div>
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
            实付款：<span id="payMoney">￥<fmt:formatNumber value="${sessionScope.totalPrice}" pattern="#0.00"/>元</span>
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
            $("#pay-b").text(0);
            var totalBhPoints = parseInt($("#totalBhPoints").val());
            if (needPay < totalBhPoints) {
                payMoney.text(0);
                $("#pay-a").text(needPay);
            } else {
                payMoney.val(payMoney - totalBhPoints);
                $("#pay-a").text(totalBhPoints);
            }
        } else {
            $("#pay-a").text(0);
            var totalQianPoints = parseInt($("#totalQianPoints").val());
            if (needPay < totalQianPoints) {
                payMoney.text(0);
                $("#pay-b").text(needPay);
            } else {
                payMoney.val(payMoney - totalQianPoints);
                $("#pay-b").text(totalQianPoints);
            }
        }
    }

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

        var baodou = parseInt($("#baodou").val());
        var needPay = parseFloat($("#totalPrice").val()), needPay = needPay.toFixed(2);
        if (baodou > 0) {
            needPay = needPay - baodou / 100;
        }

        var paymentMethod = $("#paymentMethod").val();
        var payPassword = $("#payPassword").val();
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
            var cartProductIds = $("#cartProductIds").val();
            var addressId = $("#addressId").val();
            var baodou = parseInt($("#baodou").val());
            var payPassword = $("#payPassword").val();
            $.ajax({
                url : 'api/order',
                type : 'POST',
                data : {
                    cartProductIds : cartProductIds,
                    paymentMethod : paymentMethod,
                    baodou : baodou,
                    addressId : addressId,
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
                                break;
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
