<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>收货地址</title>
    <link rel="stylesheet" type="text/css" href="resources/css/address.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="新增地址" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div class="new-addr">
    <div class="new-info-box">
        <div class="new-set-info">
            <span class="new-txt2 new-mg-b5">收货人姓名</span> 
            <span class="new-input-span new-mg-b10">
                <input type="text" name="name" id="name" maxlength="25" class="new-input" value="" autocomplete="off">
            </span>
            <span class="new-txt-err" id="name_error"></span>
            <span class="new-txt2 new-mg-b5">手机号码</span>
            <span class="new-span-block new-mg-b10">
                <span class="new-input-span">
                    <input type="text" name="mobile" id="mobile" class="new-input" value="" autocomplete="off">
                </span>
                <span class="new-txt-err" id="mobile_error"></span>
            </span>
        </div>
        <div class="new-ship-addr">
            <strong class="new-tit">收货地址</strong>
            <div class="new-mg-t10">
                <span class="new-tbl-type new-mg-b10">
                    <span class="new-tbl-cell new-txt-w38">省份</span>
                    <span class="new-tbl-cell">
                        <span class="new-input-span new-mg-b10">
                            <span class="new-sel-box new-p-re">
                                <div id="province_text">请选择</div>
                                <span></span> 
                                <select id="address_province" class="new-select">
                                    <option value="0">请选择</option>
                                    <c:forEach items="${firstLocations}" var="firstLocation">
                                        <option value="${firstLocation.locationId}" id="option${firstLocation.locationId}">${firstLocation.name}</option>
                                    </c:forEach>
                                </select>
                            </span>
                        </span>
                    </span>
                </span>
            </div>
            <div class="new-mg-t10">
                <span class="new-tbl-type new-mg-b10">
                    <span class="new-tbl-cell new-txt-w38">城市</span>
                    <span class="new-tbl-cell">
                        <span class="new-input-span new-mg-b10">
                            <span class="new-sel-box new-p-re">
                                <div id="city_text">请选择</div>
                                <span></span> 
                                <select id="address_city" class="new-select">
                                    <option value="0">请选择</option>
                                </select>
                            </span>
                        </span>
                    </span>
                </span>
            </div>
            <div class="new-mg-t10">
                <span class="new-tbl-type new-mg-b10">
                    <span class="new-tbl-cell new-txt-w38">区县</span>
                    <span class="new-tbl-cell">
                        <span class="new-input-span new-mg-b10">
                            <span class="new-sel-box new-p-re">
                                <div id="location_text">请选择</div>
                                <span></span> 
                                <select id="address_location" class="new-select">
                                    <option value="0">请选择</option>
                                </select>
                            </span>
                        </span>
                    </span>
                </span>
                <span class="new-txt-err" id="location_error"></span>
            </div>
            <div class="new-mg-t10">
                <span class="new-tbl-type new-mg-b10">
                    <span class="new-tbl-cell new-txt-w38">街道</span>
                    <span class="new-tbl-cell">
                        <div class="new-post_wr">
                            <textarea name="address" id="address" rows="5" cols="30" class="new-textarea"></textarea>
                        </div>
                        <span class="new-txt-err" id="address_error"></span>
                    </span>
                </span>
            </div>
        </div>
        <a href="javascript:void(0);" id="address_submit" class="new-abtn-type new-mg-t15">保存地址</a>
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
	var checkName = function(name) {
		name = name || '';
		return (name.length >= 2 && name.length <= 20);
	}, checkMobile = function(mobile) {
		mobile = mobile || '';
		return (mobile.length == 11 && mobile.match(/^1\d{10}$/));
	}, checkAddress = function(address) {
		address = address || '';
		return (address.length >= 1 && address.length <= 200);

	};

	var getChildren = function(parentId, elementId) {
		var selector = $("#" + elementId);
		selector.html("<option value='0'>请选择</option>");
		$.ajax({
			url : 'api/locations/' + parentId,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				$.each(data, function(index, value) {
					selector.append("<option value='" + value.locationId + "' id='option" + value.locationId + "'>" + value.name + "</option>");
				});
			}
		});
	}

	$(function() {
		var nameEl = $("#name"), mobileEl = $("#mobile"), locationEl = $("#address_location"), addressEl = $("#address");
		var nameErr = $("#name_error"), mobileErr = $("#mobile_error"), locationErr = $("#location_error"), addressErr = $("#address_error");
		nameEl.on("change", function() {
			if (!checkName($(this).val())) {
				nameErr.html("姓名2-20位字符");
			} else {
				nameErr.html("");
			}
		});
		mobileEl.on("change", function() {
			if (!checkMobile($(this).val())) {
				mobileErr.html("手机号码格式不正确");
			} else {
				mobileErr.html("");
			}
		});
		locationEl.on("change", function() {
			if ($(this).val() <= 0) {
				locationErr.html("地区不能为空");
			} else {
				locationErr.html("");
			}
		});
		addressEl.on("change", function() {
			if (!checkAddress($(this).val())) {
				addressErr.html("街道信息不能为空");
			} else {
				addressErr.html("");
			}
		});

		$("#address_submit").on("click", function() {
			if (!checkName(nameEl.val())) {
				nameErr.html("姓名2-20位字符");
				return;
			}
			if (!checkMobile(mobileEl.val())) {
				mobileErr.html("手机号码格式不正确");
				return;
			}
			if (locationEl.val() <= 0) {
				locationErr.html("地区不能为空");
				return;
			}
			if (!checkAddress(addressEl.val())) {
				addressErr.html("街道信息不能为空");
				return;
			}

			$.ajax({
				url : 'api/createAddress',
				type : 'POST',
				data : {
					name : nameEl.val(),
					mobile : mobileEl.val(),
					locationId : locationEl.val(),
					address : addressEl.val(),
					isDefault : false
				},
				dataType : 'json',
				success : function(rdata) {
					if (rdata.ret == 0) {
						window.location.href = "${redirectURL}";
					} else {
						switch (rdata.ret) {
						case 1000:
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
					alert("保存失败");
				}
			});
		});

		var province_text = $("#province_text"), city_text = $("#city_text"), location_text = $("#location_text");
		var address_province = $("#address_province"), address_city = $("#address_city"), address_location = $("#address_location");
		address_province.on("change", function() {
			var provinceId = $(this).val();
			if (provinceId == 0) {
				province_text.text('请选择');
			} else {
				province_text.text($("#option" + provinceId).text());
				address_city.append(getChildren(provinceId, 'address_city'));
			}

			city_text.text('请选择');
			location_text.text('请选择');
			address_city.html("<option value='0'>请选择</option>");
			address_location.html("<option value='0'>请选择</option>");
		});
		address_city.on("change", function() {
			var cityId = $(this).val();
			if (cityId == 0) {
				city_text.text('请选择');
				location_text.text('请选择');
				address_location.html("<option value='0'>请选择</option>");
			} else {
				city_text.text($("#option" + cityId).text());
				address_location.append(getChildren(cityId, 'address_location'));
			}
		});
		address_location.on("change", function() {
			var locationId = $(this).val();
			if (locationId == 0) {
				location_text.text('请选择');
				address_location.html("<option value='0'>请选择</option>");
			} else {
				location_text.text($("#option" + locationId).text());
			}
		});
	});
</script>

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
