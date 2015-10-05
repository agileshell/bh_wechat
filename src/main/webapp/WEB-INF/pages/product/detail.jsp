<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../config.jsp"%>
<head>
    <title>${product.name}</title>
    <link rel="stylesheet" type="text/css" href="resources/css/product-detail.css?v=${version}" />
    <script type="text/javascript" src="resources/js/product-detail.js?v=${version}"></script>
</head>

<!-- header start -->
<c:set var="header_name" value="商品详情" />
<c:set var="current_menu" value="product-detail" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- product detail start -->
<c:if test="${product.ret != 0}">
    <c:redirect url="/error">
        <c:param name="errorMessage" value="${product.msg}"/> 
    </c:redirect>
</c:if>

<c:set var="img_count" value="0"/>
<c:forEach var="image" items="${product.images}">
    <c:set var="imgs" value="${imgs}${image},"/>
    <c:set var="img_count" value="${img_count + 1}"/>
</c:forEach>
<input type="hidden" id="productId" value="${product.productId}">
<input type="hidden" id="inventory" value="${product.inventory}">
<input type="hidden" id="imgs" value="${product.coverImg},${imgs}">
<div id="mainLayout" style="display: -webkit-box;">
    <div class="new-ct" id="mainStay" style="-webkit-box-flex: 1; width: 100%;">
        <div class="new-p-re">
            <div class="detail-img">
                <div class="tbl-type" id="_zoom">
                    <div id="imgSlider" style="position: relative; left: 0px;">
                        <span class="tbl-cell">
                            <!--default image-->
                            <img src="${product.coverImg}" seq="0" width="320" height="292">
                        </span>
                    </div>
                    <ul class="new-banner-num new-b-num-v1 new-tbl-type" id="imgSliderPage">
                        <li class="new-tbl-cell on"><a href="javascript:void(0)"></a></li>
                        <c:forEach begin="1" end="${img_count}" step="1">
                            <li class="new-tbl-cell "><a href="javascript:void(0)"></a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="detail-price">
                <c:choose>
                    <c:when test="${product.discountPrice != 0}">
                        <span class="text">&yen;<span>${product.discountPrice}</span></span>
                        <span class="del">&yen;<span>${product.price}</span></span>
                    </c:when>
                    <c:otherwise>
                        <span class="text">&yen;<span>${product.price}</span></span>
                    </c:otherwise>
                </c:choose>
                <c:if test="${product.point != 0}">
                    <span class="point">可获积分：${product.point}</span>
                </c:if>
                <!--attention-->
                <a class="btn-sc" id="attention"><span></span></a>
            </div>
        </div>
        <div class="secton10">
            <p class="detail-title">
                ${product.name}
                <c:if test="${!empty product.number}">
                    <font color="red">商品编号：${product.number}</font>
                </c:if>
            </p>
            <p class="detail-title">
                <c:choose>
                    <c:when test="${product.discountPrice != 0}">
                        可使用宝豆
                        <font color="red">
                            <fmt:formatNumber value="${product.discountPrice * product.baodouPercent * 100}" pattern="#0"/>
                        </font>个，抵扣
                        <font color="red">&yen;
                            <fmt:formatNumber value="${product.discountPrice * product.baodouPercent}" pattern="#0.00"/>
                        </font>元
                    </c:when>
                    <c:otherwise>
                        可使用宝豆
                        <font color="red">
                            <fmt:formatNumber value="${product.price * product.baodouPercent * 100}" pattern="#0"/>
                        </font>个，抵扣
                        <font color="red">&yen;
                            <fmt:formatNumber value="${product.price * product.baodouPercent}" pattern="#0.00"/>
                        </font>元
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        <div class="bg-h15"></div>
        <div class="secton10 secton10-v1">
            <div class="detail-option" id="select" style="display: block;" data="block">
                <c:if test="${!empty product.guige.color}">
                    <div class="option-section new-mg-t10">
                        <span class="text-fl">颜色：</span>
                        <div class="option" id="color">
                            <c:forEach items="${product.guige.color}" var="color" varStatus="status">
                                <a title="${color}" data="${color}" class="btn-color-op new-mg-b10 ${status.index == 0 ? 'on' : ''}">${color}</a>&nbsp;
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                <c:if test="${!empty product.guige.size}">
                    <div class="option-section new-mg-t10">
                        <span class="text-fl">尺寸：</span>
                        <div class="option" id="size">
                            <c:forEach items="${product.guige.size}" var="size" varStatus="status">
                                <a title="${size}" data="${size}" class="btn-color-op new-mg-b10 ${status.index == 0 ? 'on' : ''}">${size}</a>&nbsp;
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${product.inventory > 0}">
                        <div class="option-section new-mg-t10">
                            <span class="text-fl">数量：</span>
                            <div class="option">
                                <span class="add-del">
                                    <a class="btn-add" id="plus" onclick="reduce();"><span></span></a>
                                    <input type="text" class="new-input" value="1" id="number" onblur="modify(${product.inventory});">
                                    <a class="btn-del" id="minus" onclick="add(${product.inventory});"><span></span></a>
                                </span>
                            </div>
                        </div>
                        <div class="option-section new-mg-t10">
                            <input type="hidden" id="inventory" value="${product.inventory}"/>
                            <span style="font-size: 14px;">库存：</span><font color="red">${product.inventory}</font>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" value="0" id="number">
                        <div class="option-section new-mg-t10">
                            <input type="hidden" id="inventory" value="${product.inventory}"/>
                            <span style="font-size: 14px;">库存：</span><font color="red">无</font>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="secton10">
            <div class="info">
                <a href="product/description/${product.productId}"><span class="text">商品详情</span></a> <span class="icon-arr"></span>
            </div>
        </div>
        <div class="cart-pop" id="cart" style="display: none; position: absolute; bottom: 50%; z-index: 9999;">
            <div class="ico-succ">
                <span class="att-succ">添加成功！</span>
                <span class="cart-succ">商品已成功加入购物车</span>
            </div>
            <div class="cp-lnk">
                <a href="javascript:void(0);" onclick="$('#cart').hide();$('#_mask').hide();" id="stroll">再逛逛</a>
                <a href="myCart">去购物车</a>
            </div>
        </div>
        <div class="bg-h15"></div>
        <div class="tbl-type detail-tbn2" id="cart1" style="position: fixed; left: 0px; bottom: 0px; z-index: 10; display: table;">
            <div class="tbl-cell">
                <a id="directorder" class="btn-buy"><span></span>立即购买</a>
            </div>
            <div class="tbl-cell">
                <a id="add_cart" class="btn-cart"><span></span>加入购物车</a>
            </div>
        </div>
    </div>
</div>
<div class="new-gd-view-img" id="popImg"
    style="position: absolute; z-index: 9000; left: 801.5px; top: 139.5px; -webkit-transform-origin: 0px 0px; opacity: 1; -webkit-transform: scale(1, 1); display: none;"
    onclick="hidePop()">
    <div class="new-banner new-p-re">
        <ul class="new-tbl-type" style="position: absolute; margin-left: 0px;" id="bigImgSlider">
            <li class="new-tbl-cell">
                <a href="javascript:void(0)">
                    <!--default image-->
                    <span class="new-shade-img"><img src="${product.coverImg}" seq="0" alt="" width="300" height="300"></span>
                </a>
            </li>
        </ul>
        <ul class="new-banner-num new-tbl-type" id="bigImgSliderPage" style="-webkit-transform-origin: 0px 0px; opacity: 1; -webkit-transform: scale(1, 1);">
            <li class="new-tbl-cell on"><a href="javascript:void(0)"></a></li>
            <c:forEach begin="1" end="${img_count}" step="1">
                <li class="new-tbl-cell "><a href="javascript:void(0)"></a></li>
            </c:forEach>
        </ul>
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

<script>
	$("#color a").on("click", function() {
		$("#color a").removeClass("on");
		$(this).addClass("on");
	});
	$("#size a").on("click", function() {
		$("#size a").removeClass("on");
		$(this).addClass("on");
	});

	var Q = function() {
		var inventory = parseInt($('#inventory').val()), qty = parseInt($("#number").val());
		if(inventory == 0 || inventory < qty) {
			alert("库存不足");
			return;
		}
		var productId = $("#productId").val(), guige = {
			color : '',
			size : ''
		};
		if ($("#color .on").length > 0) {
			guige.color = $("#color .on").attr("data");
		}
		if ($("#size .on").length > 0) {
			guige.size = $("#size .on").attr("data");
		}
		addWare(productId, qty, JSON.stringify(guige), false);
	};

	var M = function() {
		var inventory =  parseInt($('#inventory').val()), qty =  parseInt($("#number").val());
		if(inventory == 0 || inventory < qty) {
			alert("库存不足");
			return;
		}
		var productId = $("#productId").val(), guige = {color : '',size : ''};
		if ($("#color .on").length > 0) {
			guige.color = $("#color .on").attr("data");
		}
		if ($("#size .on").length > 0) {
			guige.size = $("#size .on").attr("data");
		}
		addWare(productId, qty, JSON.stringify(guige), true);
	};
	$("#add_cart").click(Q);
	$("#directorder").click(M);
	var addWare = function(productId, qty, guige, isDirectOrder) {
		if (isDirectOrder) {
			if (parseInt($("#inventory").val()) < qty) {
				alert("库存不足");
			} else {
				window.location.href = "checkoutDirectly?productId=" + productId + "&qty=" + qty + "&guige=" + guige;
			}
		} else {
			$.ajax({
				url : 'api/addProductIntoCart',
				type : 'POST',
				data : {
					productId : productId,
					qty : qty,
					guige : guige
				},
				dataType : 'json',
				success : function(rdata) {
					if (rdata.ret == 0) {
						showMsg();
					} else {
						switch (rdata.ret) {
						case 1000:
						case 3000:
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
					alert("添加失败");
				}
			});
		}
	};
	var showMsg = function() {
        if ($("#_mask")) {
            $("#_mask").remove()
        }
        var k = ((document.body || document.documentElement).clientHeight + 20) + "px";
        var m = document.createElement("div");
        m.setAttribute("id", "_mask");
        m.setAttribute(
                        "style",
                        "position:absolute;left:0px;top:0px;background-color:rgb(20,20,20);filter:alpha(opacity=60);opacity:0.6;width:100%;height:"
                                + k + ";z-index:9998;");
        (document.body || document.documentElement).appendChild(m);
        var p = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
        var r = document.documentElement.clientHeight || document.body.clientHeight;
        document.getElementById("cart").style.bottom = ((r - 100) / 2 - p) + "px";
        $("#cart").show();
	};
</script>
<!-- product detail end -->

<!-- footer start -->
<div id="footer" style="padding-bottom: 3.0em;">
    <%@ include file="../../layout/_footer.jsp"%>
</div>
<!-- footer end -->
