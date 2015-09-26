<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>宝汇币提现</title>
    <link rel="stylesheet" type="text/css" href="resources/css/currency.css?v=${version}" />
    <style>
    .input-box {
      position: relative;
      background-color: #fff;
      padding-bottom: 10px;
    }
    .input-box input {
      padding-left: 60px;
      height: 50px;
    }
    .beforeInput {
      display: inline-block;
      position: absolute;
      top: 0;
      left: 10px;
      line-height: 25px;
      padding: 15px 0;
      font-size: 15px;
    }
    .enter {
      display: block;
      width: 100%;
      line-height: 25px;
      padding: 15px 0;
      font-size: 15px;
      color: #333;
    }
    .warn-input {
      height: 2.1rem;
      line-height: 1.9rem;
      font-size: .6rem;
      color: #e85156;
      text-indent: .938rem;
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
    <div style="padding: 20px 10px 0 10px;">
        <section class="input-box">
            <span class="beforeInput">开户行</span>
            <input id="bankName" name="bankName" class="enter" type="text" maxlength="30" placeholder="请输入开户行">
        </section>
        <section class="input-box">
            <span class="beforeInput">开户名</span>
            <input id="bankAccountName" name="bankAccountName" class="enter" type="text" maxlength="30" placeholder="请输入开户名">
        </section>
        <section class="input-box">
            <span class="beforeInput">卡号</span>
            <input id="bankAccountID" name="bankAccountID" class="enter" type="text" maxlength="30" placeholder="请输入卡号">
        </section>
        <section class="input-box">
            <span class="beforeInput">金额</span>
            <input id="amount" name="amount" class="enter" type="tel" maxlength="9" placeholder="请输入提现金额">
        </section>
        <section class="input-box">
            <span class="beforeInput">密码</span>
            <input id="payPassword" name="payPassword" class="enter" type="password" maxlength="20" placeholder="请输入唯宝汇支付密码">
        </section>
        <section id="warn-input" class="warn-input"></section>
    </div>
    <div>
        <span style="display:block;">
            <a id="recharge" href="javascript:void(0);" class="btn btn-recharge" style="margin: 0 8% 0 8%;">下一步</a>
        </span>
    </div>
</div>
<script>
    $("#recharge").on("click", function() {
        var bankName = $("#bankName").val();
        if(bankName.length <= 0) {
            $("#bankName").focus();
            $("#warn-input").text("开户行格式错误，请重新输入");
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

        var payPassword = $("#payPassword").val();
        if(payPassword.length <= 0) {
            $("#payPassword").focus();
            $("#warn-input").text("支付密码格式错误，请重新输入");
            return;
        }

        $.ajax({
            url : 'api/withdraw',
            type : 'POST',
            data : {
                bankName : bankName,
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
            }
        });
    });
</script>

<!-- footer start -->
<div style="clear: both; padding-top: 30px">
    <%@ include file="../../layout/_footer.jsp"%>
</div>
<!-- footer end -->