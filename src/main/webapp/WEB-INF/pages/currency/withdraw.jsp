<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>宝汇币提现</title>
    <link rel="stylesheet" type="text/css" href="resources/css/currency.css?v=${version}" />
    <style>
	.new-recharge {
		padding: 20px 12px;
	}
	.new-recharge .new-txt2 {
		display: block;
		font-size: 14px;
		color: #333;
	}
	.new-recharge .new-tit {
		font-size: 18px;
		color: #6e6e6e;
	}
	.new-recharge .new-tbl-cell {
		font-size: 14px;
		color: #6e6e6e;
		background: none;
		vertical-align: top;
	}
	.new-txt-w38 {
		width: 65px;
		height: 32px;
		line-height: 32px;
		text-align: left;
	}
	.new-box {
		display: inline-block;
		width: 90px;
		height: 20px;
		margin-left: 5px;
		border: 1px solid #707070;
		background-color: #fff;

		font-size: 12px;
  	    font-weight: normal;
  	    color: #bdbdbd;
	}
	.new-select {
		position: absolute;
		top: 0;
		left: 0;
		z-index: 10;
		width: 100%;
		height: 30px;
		opacity: 0;
	}
	.new-recharge .new-box {
		width: 100%;
		height: 30px;
		margin-left: 0;
		border: 0;
		line-height: 30px;
		font-size: 12px;
		text-indent: 10px;
	}
    .info-input {
      line-height: 15px;
      font-size: 12px;
      color: #bdbdbd;
    }
    .warn-input {
      height: 15px;
      line-height: 15px;
      font-size: 12px;
      color: #e85156;
    }
    </style>
</head>

<!-- header start -->
<c:set var="header_name" value="宝汇币提现" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="bhPoints" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div>
    <div class="new-recharge">
   		<div class="new-mg-t10">
	         <span class="new-tbl-type new-mg-b10">
	             <span class="new-tbl-cell new-txt-w38">省份</span>
	             <span class="new-tbl-cell">
	                 <span class="new-input-span">
	                     <span class="new-box new-p-re">
	                         <div id="province_text">请选择开户行所在省</div>
	                         <span></span> 
	                         <select id="address_province" class="new-select">
	                             <option value="0">请选择开户行所在省</option>
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
	                 <span class="new-input-span">
	                     <span class="new-box new-p-re">
	                         <div id="city_text">请选择开户行所在城市</div>
	                         <span></span> 
	                         <select id="address_city" class="new-select">
	                             <option value="0">请选择开户行所在城市</option>
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
                    <span class="new-input-span">
                        <span class="new-box new-p-re">
                            <div id="location_text">请选择开户行所在区县</div>
                            <span></span> 
                            <select id="address_location" class="new-select">
                                <option value="0">请选择开户行所在区县</option>
                            </select>
                        </span>
                    </span>
                </span>
            </span>
        </div>
        <div class="new-mg-t10">
            <span class="new-tbl-type new-mg-b10">
                <span class="new-tbl-cell new-txt-w38">开户银行</span>
                <span class="new-tbl-cell">
                    <span class="new-input-span">
                        <span class="new-box new-p-re">
                            <div id="bank_text">请选择开户银行</div>
                            <span></span> 
                            <select id="bank" class="new-select">
                                <option value="">请选择开户银行</option>
                                <option value="中国银行">中国银行</option>
                                <option value="中国招商银行">中国招商银行</option>
                                <option value="中国农业银行">中国农业银行</option>
                                <option value="中国工商银行">中国工商银行</option>
                                <option value="中国建设银行">中国建设银行</option>
                                <option value="中国邮政储蓄银行">中国邮政储蓄银行</option>
                                <option value="中国中信银行">中国中信银行</option>
                                <option value="中国交通银行">中国交通银行</option>
                                <option value="中国光大银行">中国光大银行</option>
                                <option value="中国兴业银行">中国兴业银行</option>
                                <option value="中国农业发展银行">中国农业发展银行</option>
                                <option value="中国华夏银行">中国华夏银行</option>
                                <option value="中国民生银行">中国民生银行</option>
                                <option value="中国浦东发展银行">中国浦东发展银行</option>
                            </select>
                        </span>
                    </span>
                </span>
            </span>
            <span class="info-input">优先处理农业银行</span>
        </div>
        <div class="new-mg-t10">
        	<span class="new-tbl-type new-mg-b10">
        		<span class="new-tbl-cell new-txt-w38">开户支行</span>
        		<span class="new-input-span">
        			<input type="text" id="sub_bank_name" class="new-input" autocomplete="off" placeholder="请输入开户支行">
        		</span>
        	</span>
        </div>
        <div class="new-mg-t10">
        	<span class="new-tbl-type new-mg-b10">
        		<span class="new-tbl-cell new-txt-w38">开户名</span>
        		<span class="new-input-span">
        			<input type="text" name="bankAccountName" id="bankAccountName" class="new-input" autocomplete="off" placeholder="请输入开户名">
        		</span>
        	</span>
        </div>

        <div class="new-mg-t10">
        	<span class="new-tbl-type new-mg-b10">
        		<span class="new-tbl-cell new-txt-w38">银行卡号</span>
        		<span class="new-input-span">
        			<input type="text" name="bankAccountID" id="bankAccountID" class="new-input" autocomplete="off" placeholder="请输入银行卡号">
        		</span>
        	</span>
        </div>
        <div class="new-mg-t10">
        	<span class="new-tbl-type new-mg-b10">
        		<span class="new-tbl-cell new-txt-w38">提现金额</span>
        		<span class="new-input-span">
        			<input type="text" name="amount" id="amount" class="new-input" autocomplete="off" placeholder="请输入提现金额">
        		</span>
        	</span>
        </div>
        <div class="new-mg-t10">
        	<span class="new-tbl-type new-mg-b10">
        		<span class="new-tbl-cell new-txt-w38">支付密码</span>
        		<span class="new-input-span">
        			<input type="password" name="payPassword" id="payPassword" class="new-input" autocomplete="off" placeholder="请输入唯宝汇支付密码">
        		</span>
        	</span>
        </div>
        <section id="warn-input" class="warn-input"></section>
        <div>
        <span class="info-input">1、每次提现最小金额为100元；</span>
            <br/>
            <span class="info-input">2、提现收取1%的手续费，对于手续费不满两元的按照最低手续费两元收取；</span>
            <br/>
            <span class="info-input">3、支付密码默认为您的登录密码,您可以在个人中心进行修改。</span>
        </div>
    </div>
    <div>
        <span id="rechargespan"  style="display:block;">
            <a id="recharge" href="javascript:void(0);" class="btn btn-recharge" style="margin: 0 8% 0 8%;">下一步</a>
        </span>
    </div>
</div>
<script>
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
	
	var province_text = $("#province_text"), city_text = $("#city_text"), location_text = $("#location_text"), bank_text = $("#bank_text");
	var address_province = $("#address_province"), address_city = $("#address_city"), address_location = $("#address_location"), bank = $("#bank");
	address_province.on("change", function() {
		var provinceId = $(this).val();
		if (provinceId == 0) {
			province_text.text('请选择开户行所在省');
		} else {
			province_text.text($("#option" + provinceId).text());
			address_city.append(getChildren(provinceId, 'address_city'));
		}

		city_text.text('请选择开户行所在城市');
		location_text.text('请选择开户行所在区县');
		address_city.html("<option value='0'>请选择开户行所在城市</option>");
		address_location.html("<option value='0'>请选择开户行所在区县</option>");
	});
	address_city.on("change", function() {
		var cityId = $(this).val();
		if (cityId == 0) {
			city_text.text('请选择开户行所在城市');
			location_text.text('请选择开户行所在区县');
			address_location.html("<option value='0'>请选择开户行所在区县</option>");
		} else {
			city_text.text($("#option" + cityId).text());
			address_location.append(getChildren(cityId, 'address_location'));
		}
	});
	address_location.on("change", function() {
		var locationId = $(this).val();
		if (locationId == 0) {
			location_text.text('请选择开户行所在区县');
			address_location.html("<option value='0'>请选择开户行所在区县</option>");
		} else {
			location_text.text($("#option" + locationId).text());
		}
	});
	bank.on("change", function() {
		var bankname = $(this).val();
		if (bankname.length <=0 ) {
			bank_text.text('请选择开户银行');
			bank_name.html("<option value=''>请选择开户银行</option>");
		} else {
			bank_text.text(bankname);
		}
	});

    $("#recharge").on("click", function() {
    	var bankNameProvince = $("#address_province").val();
        if(bankNameProvince <= 0) {
            $("#address_province").focus();
            $("#warn-input").text("请选择开户行所在省");
            return;
        }
    	var bankNameCity = $("#address_city").val();
        if(bankNameCity <= 0) {
            $("#address_city").focus();
            $("#warn-input").text("请选择开户行所在城市");
            return;
        }
    	var bankNameLocation = $("#address_location").val();
        if(bankNameLocation <= 0) {
            $("#address_location").focus();
            $("#warn-input").text("请选择开户行所在区县");
            return;
        }
        var bankName = $("#bank").val();
        if(bankName.length <= 0) {
            $("#bank").focus();
            $("#warn-input").text("请选择开户银行");
            return;
        }
        var subBankName = $("#sub_bank_name").val();
        if(subBankName.length <= 0) {
            $("#sub_bank_name").focus();
            $("#warn-input").text("请选择开户支行");
            return;
        }

        var bankAccountName = $("#bankAccountName").val();
        if(bankAccountName.length <= 0) {
            $("#bankAccountName").focus();
            $("#warn-input").text("开户名格式错误，请重新输入");
            return;
        }

        var bankAccountID = $("#bankAccountID").val();
        if(bankAccountID.length <= 0) {
            $("#bankAccountID").focus();
            $("#warn-input").text("卡号格式错误，请重新输入");
            return;
        }

        var amount = $("#amount").val();
        var reg = /^\d+(?=\.{0,1}\d+$|$)/
        if(!reg.test(amount)) {
            $("#amount").focus();
            $("#warn-input").text("金额格式错误，请重新输入");
            return;
        }
        if(amount < 100) {
            alert("提现金额不能少于100元");
            return;
        }

        var payPassword = $("#payPassword").val();
        if(payPassword.length <= 0) {
            $("#payPassword").focus();
            $("#warn-input").text("支付密码格式错误，请重新输入");
            return;
        }

        $("#rechargespan").hide();
        bankFullName = $("#option" + bankNameProvince).text() + ' ' + $("#option" + bankNameCity).text() + ' ' + $("#option" + bankNameLocation).text() + ' ' + bankName + ' ' + subBankName;
        $.ajax({
            url : 'api/withdraw',
            type : 'POST',
            data : {
                bankName : bankFullName,
                bankAccountName : bankAccountName,
                bankAccountID : bankAccountID,
                amount : amount,
                payPassword: payPassword
            },
            dataType : 'json',
            success : function(rdata) {
                if (rdata.ret == 0) {
                    window.location.href = 'bhPoints/withdraw/history';
                } else {
                    $("#rechargespan").show();
                    switch (rdata.ret) {
                        case 1000:
                        case 3002:
                        case 2011:
                            alert(rdata.msg);
                            break;
                        default:
                            alert(rdata.msg);
                            break;
                    }
                }
            },
            error : function() {
                alert("提现失败");
                $("#rechargespan").show();
            }
        });
    });
</script>

<!-- footer start -->
<div style="clear: both; padding-top: 30px">
    <%@ include file="../../layout/_footer.jsp"%>
</div>
<!-- footer end -->