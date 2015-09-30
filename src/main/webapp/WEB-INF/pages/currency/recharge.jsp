<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>宝汇币充值</title>
    <link rel="stylesheet" type="text/css" href="resources/css/currency.css?v=${version}" />
    <style>
    .input-box {
      position: relative;
      background-color: #fff;
    }
    .input-box input {
      padding-left: 50px;
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
<c:set var="header_name" value="宝汇币充值" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="bhPoints" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<form id="goRechargeForm" action="goRecharge" method="get">
    <div style="padding: 50px 10px 0 10px;">
        <section class="input-box">
            <span class="beforeInput">金额</span>
            <input id="amount" name="amount" class="enter" type="tel" maxlength="9" placeholder="请输入充值金额（单位：元）">
        </section>
        <section id="warn-input" class="warn-input"></section>
    </div>
    <div>
        <span style="display:block;">
            <a id="recharge" href="javascript:void(0);" class="btn btn-recharge" style="margin: 0 8% 0 8%;">下一步</a>
        </span>
    </div>
</form>
<script>
    $("#recharge").on("click", function() {
        var amount = $("#amount").val();
        var reg = /^\d+(?=\.{0,1}\d+$|$)/
        if(reg.test(amount)) {
            $("#goRechargeForm").submit();
        } else {
            $("#warn-input").text("金额格式错误，请重新输入");
            return;
        }
    });
</script>

<!-- footer start -->
<div style="clear: both; padding-top: 30px">
    <%@ include file="../../layout/_footer.jsp"%>
</div>
<!-- footer end -->