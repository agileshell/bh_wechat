<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>收货地址</title>
    <link rel="stylesheet" type="text/css" href="resources/css/address.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="收货地址管理" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="profile" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div class="new-addr">
    <ul class="address-ul">
        <c:forEach items="${addresses}" var="address">
        <li class="address-li">
            <p class="new-tit new-p-re">
                <span class="new-txt">${address.name}</span> <span class="new-txt-rd2">${address.mobile}</span>
                <c:if test="${address.isDefault == true}">
                    <i class="sitem-tip">默认</i>
                </c:if>
                <span class="new-txt-rd2 new-option-r" addressId="${address.addressId}">默认地址</span>
            </p>
            <span class="new-mu_l2a new-p-re">
                <span class="new-mu_l2cw">${address.fullAddress}</span>
                <div class="new-addr-btn">
                    <a href="updateAddress/${address.addressId}">编辑</a><span class="new-addr-bar">| </span><a href="javascript:void(0);" addressId="${address.addressId}" onclick="delClick(this);">删除</a>
                </div>
            </span>
        </li>
        </c:forEach>
    </ul>
    <a href="createAddress" class="new-abtn-type new-mg-tb30">添加新地址</a>
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
<script type="text/javascript">
    function delClick(param) {
        if (confirm("确定要删除该收货地址吗？")) {
            var addressId = $(param).attr("addressId");
            $.ajax({
                url : 'api/deleteAddress',
                type : 'POST',
                data : {
                    addressId : addressId
                },
                dataType : 'json',
                success : function(rdata) {
                    if (rdata.ret == 0) {
                        window.location.href = 'addresses';
                    } else {
                        switch (rdata.ret) {
                            case 1000:
                            case 2100:
                            case 2101:
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
                    alert("删除失败");
                }
            });
        }
    }

    $(".new-option-r").on("click", function() {
        if (confirm("确定要设置为默认地址吗？")) {
            var addressId = $(this).attr("addressId");
            $.ajax({
                url : 'api/setDefaultAddress',
                type : 'POST',
                data : {
                    addressId : addressId
                },
                dataType : 'json',
                success : function(rdata) {
                    if (rdata.ret == 0) {
                        window.location.href = 'addresses';
                    } else {
                        switch (rdata.ret) {
                            case 1000:
                            case 2100:
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
                    alert("设置为默认地址失败");
                }
            });
        }
    });
</script>

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
