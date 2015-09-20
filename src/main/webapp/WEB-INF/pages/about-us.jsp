<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="config.jsp"%>
<head>
    <title>关于我们</title>
    <script type="text/javascript" src="resources/js/m.scale.js?v=${version}"></script>
    <style>
    .detail {
        position: relative;
        padding: 5px 0 0 15px;
        font-size: .75em;
        color: #5a5a5a;
    }
    .scale-box {
        display: table;
    }
    .scale-box img {
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
    </style>
</head>

<!-- header start -->
<c:set var="header_name" value="关于我们" />
<%@ include file="../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- description start -->
<div class="detail" id="wareInfo">
    <div id="scale-parent">
        <div class="scale-box" id="scale-cont">
            <!-- content start -->
            <p>
                <span style="color: #a9a9a9;">1、如果您有什么意见或者建议的话，或需要了解宝汇网的商城的相关问题都可以联系我们的免费400客服电话：</span>
            </p>
            <p>
                <span style="color: #a9a9a9;"><strong>&nbsp; &nbsp; &nbsp; 商城的400 </strong><strong>免费 </strong><strong>服务 </strong><strong>电话 </strong><strong>：&nbsp;&nbsp;
                        4008 --616---687 (</strong>工作时间 （8：30—18：00</span>）<strong style="color: rgb(169, 169, 169);">)</strong>
            </p>
            <p>
                <strong style="color: rgb(169, 169, 169);">&nbsp; &nbsp; &nbsp; 您也可以 </strong><strong style="color: rgb(169, 169, 169);">发邮件到我们的公共邮箱：
                    yideqian2015@163.com</strong>
            </p>
            <p>
                <span style="color: #a9a9a9;">2、如果您需要协商 产品合作 的话 请拨打我们的产品部电话</span>
            </p>
            <p>
                <span style="color: #a9a9a9;"><strong>&nbsp; &nbsp; &nbsp; 产品服务电话&nbsp; </strong><strong>： 0311--85259010&nbsp; </strong><strong>联系人
                </strong><strong>：刘经理</strong></span>
            </p>
            <p>
                <span style="color: #a9a9a9;"><strong>&nbsp; &nbsp; &nbsp; 产品服务电话&nbsp; </strong><strong>：13522105505</strong></span>
            </p>
            <p>
                <span style="color: #a9a9a9;"><strong>&nbsp; &nbsp; &nbsp;产品部邮箱： &nbsp;</strong>&nbsp;<strong>&nbsp;7945376@qq.com</strong></span>
            </p>
            <p>
                <strong><span style="color: #a9a9a9;">&nbsp; &nbsp; &nbsp; (工作时间 （8：30—18：00）)</span></strong>
            </p>
            <p>
                <span style="color: #a9a9a9;">3、如果您有售后退货方面的问题 请拨打我们的售后部电话：</span>
            </p>
            <p>
                <span style="color: #a9a9a9;"><strong>&nbsp; &nbsp; &nbsp; 售后部电话：4008--616--687 &nbsp;&nbsp;</strong></span><strong><span
                    style="color: rgb(169, 169, 169);">(工作时间 （8：30—18：00）)</span></strong>
            </p>
            <p>
                <span style="color: #a9a9a9;">4. 如果你有市场问题的话，您可以拨打我们的市场部电话</span>
            </p>
            <p>
                <span style="color: #a9a9a9;"><strong>&nbsp; &nbsp; &nbsp;市场部电话：13522105565&nbsp; </strong><strong>联系人 </strong><strong>：付经理</strong></span>
            </p>
            <!-- content end -->
        </div>
    </div>
</div>
<script type="text/javascript">
	scale.ready(function() {
		scale.init();
	});
</script>
<!-- description end -->

<!-- footer start -->
<%@ include file="../layout/_footer.jsp"%>
<!-- footer end -->
