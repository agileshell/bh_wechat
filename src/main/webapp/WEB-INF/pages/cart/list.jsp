<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../config.jsp"%>
<head>
    <title>购物车</title>
    <link rel="stylesheet" type="text/css" href="resources/css/cart.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="购物车" />
<c:set var="current_menu" value="cart" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div id="emptyCart" style="${fn:length(cartProducts) != 0 ? 'display: none' :''}">
    <div class="shp-cart-empty">
        <div class="cart-empty-pic"></div>
        <div class="empty-warning-text">购物车空空如也，快去购物吧</div>
        <div class="empty-btn-wrapper">
            <a href="products" class="btn-jd-darkred btn-large">去逛逛</a>
        </div>
    </div>
</div>

<input type="hidden" id="cartCount" value="${fn:length(cartProducts)}">

<div id="notEmptyCart" style="display: block; margin-bottom: 90px;">
    <div class="shop-group">
        <ul class="shp-cart-list">
          <c:forEach items="${cartProducts}" var="cartProduct">
            <li id="${cartProduct.cartProductId}" class="${cartProduct.inventory >= cartProduct.qty ? 'displayed' : ''}">
                <div class="items">
                    <div class="check-wrapper">
                        <input type="hidden" id="productId${cartProduct.cartProductId}" value="${cartProduct.productId}">
                        <input type="hidden" id="name${cartProduct.cartProductId}" value="${cartProduct.name}">
                        <input type="hidden" id="coverImg${cartProduct.cartProductId}" value="${cartProduct.coverImg}">
                        <input type="hidden" id="price${cartProduct.cartProductId}" value="${cartProduct.discountPrice > 0 ? cartProduct.discountPrice :cartProduct.price}">
                        <input type="hidden" id="point${cartProduct.cartProductId}" value="${cartProduct.point}">
                        <input type="hidden" id="baodouPercent${cartProduct.cartProductId}" value="${cartProduct.baodouPercent}">
                        <input type="hidden" id="inventory${cartProduct.cartProductId}" value="${cartProduct.inventory}">
                        <span id="cart-checkbox-${cartProduct.cartProductId}" class="cart-checkbox ${cartProduct.inventory >= cartProduct.qty ? 'displayed' : 'undisplay'}" onclick="javascript:changeSelected(${cartProduct.cartProductId});"></span>
                    </div>
                    <div class="shp-cart-item-core ">
                        <div class="cart-product-cell-3">
                            <span class="shp-cart-item-price">&yen;${cartProduct.discountPrice > 0 ? cartProduct.discountPrice :cartProduct.price}</span>
                        </div>
                        <div class="cart-product-cell-1"> 
                            <img class="cart-photo-thumb" src="${cartProduct.coverImg}">
                        </div>
                        <div class="cart-product-cell-2">
                            <div class="cart-product-name">
                                <a href="product/${cartProduct.productId}"><span>${cartProduct.name}</span></a>
                            </div>
                            <c:if test="${!empty cartProduct.guige.color || !empty cartProduct.guige.size}">
                                <div class="cart-product-name2" id="guige${cartProduct.cartProductId}">
                                    <c:if test="${!empty cartProduct.guige.color}">
                                        颜色：${cartProduct.guige.color}&nbsp;
                                    </c:if>
                                    <c:if test="${!empty cartProduct.guige.size}">
                                        尺寸：${cartProduct.guige.size}
                                    </c:if>
                                </div>
                            </c:if>
                            <div class="cart-product-name2">
                                可使用宝豆
                                <font color="red">
                                    <fmt:formatNumber value="${cartProduct.discountPrice > 0 ? cartProduct.discountPrice * cartProduct.baodouPercent * 100 : cartProduct.price * cartProduct.baodouPercent * 100}" pattern="#0"/>
                                </font>个，抵扣
                                <font color="red">&yen;
                                    <fmt:formatNumber value="${cartProduct.discountPrice > 0 ? cartProduct.discountPrice * cartProduct.baodouPercent :cartProduct.price * cartProduct.baodouPercent}" pattern="#0.00"/>
                                </font>元
                            </div>
                            <div class="cart-product-name2">
                                库存：<font color="red">${cartProduct.inventory}</font>
                            </div>
                            <div class="shp-cart-opt">
                                <div class="quantity-wrapper">
                                    <input type="hidden" id="lastQty${cartProduct.cartProductId}" value="${cartProduct.qty}">
                                    <a class="quantity-decrease" href="javascript:reduceWare(${cartProduct.cartProductId})">-</a>
                                    <input type="text" class="quantity" size="4" onchange="modifyWare(${cartProduct.cartProductId})" value="${cartProduct.qty}" id="qty${cartProduct.cartProductId}">
                                    <a class="quantity-increase" href="javascript:addWare(${cartProduct.cartProductId})">+</a>
                                </div>
                                <a class="shp-cart-icon-remove" href="javascript:deleteWare(${cartProduct.cartProductId})"></a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
          </c:forEach>
        </ul>
    </div>
</div>
<div style="display: block; position: fixed; bottom: 57px; width:100%; height: 20px; font-size: 12px; background: #efefef;">订单金额满299元免运费</div>
<div id="payment_p" style="display: block">
    <div class="payment-total-bar payment-total-bar-new" id="payment">
        <div class="shp-chk shp-chk-new">
            <span onclick="checkAllHandler();" class="cart-checkbox" id="checkAll"></span><span class="cart-checkbox-text">全选</span>
        </div>
        <div class="shp-cart-info shp-cart-info-new">
            <strong class="shp-cart-total">合计：&yen;<span class="bottom-bar-price" id="cart_price">0.0</span><span id="ship_fee"></span></strong>
            <span class="bottom-total-price" style="font-size: 10px;">奖励积分：<span id="cart_point" style="font-size: 10px;">0</span></span>
        </div>
        <a class="btn-right-block btn-right-block-new" href="javascript:checkout();">去结算(<span id="checked_num">0</span>)
        </a>
    </div>
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

<script type="text/javascript" src="resources/js/cart.js?v=${version}"></script>
