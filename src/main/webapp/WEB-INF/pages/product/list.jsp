<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../config.jsp"%>
<head>
    <title>商品列表</title>
    <link rel="stylesheet" type="text/css" href="resources/css/product-list.css?v=${version}" />
    <script type="text/javascript" src="resources/js/product-list.js?v=${version}"></script>
    <script type="text/javascript" src="resources/js/template.js?v=${version}"></script>
</head>

<a id="layout_top" name="top"></a>

<!-- header start -->
<header>
    <div class="header-bar ${empty requestData.keyword ? 'on-blur' : 'on-focus'}" id="layout_search_head">
        <div id="layout_search_bar_cancel" class="header-icon-cancel">
            <span>取消</span>
        </div>
        <div id="layout_urlblack" data-url="home" class="header-icon-back">
            <span>返回</span>
        </div>
        <form action="products" method="get" id="layout_searchForm" class="header-search-form">
            <div class="header-search-box">
                <div class="header-search-input">
                    <input id="layout_search_keyword" maxlength="20" name="keyword" type="text" cleardefault="no" autocomplete="off" value="${requestData.keyword}">
                </div>
            </div>
            <a href="javascript:void(0)" style="${empty requestData.keyword ? 'display: none;' : ''}" id="layout_search_submit" class="header-icon-search"><span>搜索</span></a>
        </form>
        <div class="header-icon-shortcut" id="layout_menuKey">
            <span>菜单</span>
        </div>
    </div>
    <ul id="layout_menuBar" class="header-shortcut" style="display: none;">
        <li><a href="home"> <span class="shortcut-home"></span> <strong>首页</strong></a></li>
        <li><a href="categories"> <span class="shortcut-categories"></span> <strong>分类搜索</strong></a></li>
        <li><a href="myCart"> <span class="shortcut-cart"></span> <strong>购物车</strong></a></li>
        <li><a href="profile"> <span class="shortcut-my-account"></span> <strong>我的账户</strong></a></li>
    </ul>
</header>
<!-- header end -->

<!-- products start -->
<input name="tag" type="hidden" value="${requestData.tag}">
<input name="categoryId" type="hidden" value="${requestData.categoryId}">
<div id="list">
    <input type="hidden" id="current_page" value="1"/>
    <ul class="list_body">
      <c:forEach items="${products}" var="product">
        <li>
            <a style="text-decoration: none;" href="product/${product.productId}">
                <div class="list-thumb">
                    <img width="85" height="85" src="${product.coverImg}">
                </div>
                <div class="list-descriptions">
                    <div class="list-descriptions-wrapper">
                        <div class="product-name">${product.name}</div>
                        <div class="price-spot">
                        <c:choose>
                            <c:when test="${product.discountPrice != 0}">
                                <span class="product-price">￥<span>${product.discountPrice}</span></span>
                                <span class="product-original-price">￥<span>${product.price}</span></span>
                            </c:when>
                            <c:otherwise>
                                <span class="product-price">￥<span>${product.price}</span></span>
                            </c:otherwise>
                        </c:choose>
                        </div>
                    </div>
                </div>
            </a>
        </li>
      </c:forEach>
    </ul>
    <c:choose>
        <c:when test="${fn:length(products) == 0}">
            <div class="not-found">
                <div class="notice">抱歉，没有找到符合条件的商品</div>
            </div>
        </c:when>
        <c:when test="${fn:length(products) < 10}">
            <div class="load-more" style="text-align:center;">
                <span style="font-size:12px;" id="fetchMoreMsg" >没有了</span>
            </div>
        </c:when>
        <c:otherwise>
            <div class="load-more" style="text-align:center;" id="fetchMore">
                <span style="font-size:12px;" id="fetchMoreMsg" >加载更多</span>
            </div>
        </c:otherwise>
    </c:choose>
    <div style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1); display: none;" id="back_to_top" class="right-opera">
        <ul>
            <li id="back-top"></li>
            <li id="go-home"></li>
        </ul>
    </div>
</div>
<script id="products-template" type="text/html">
    {{each list}}
        <li>
            <a style="text-decoration: none;" href="product/{{$value.productId}}">
                <div class="list-thumb">
                    <img width="85" height="85" src="{{$value.coverImg}}">
                </div>
                <div class="list-descriptions">
                    <div class="list-descriptions-wrapper">
                        <div class="product-name">{{$value.name}}</div>
                        <div class="price-spot">
                        {{if $value.discountPrice != 0 }}
                            <span class="product-price">￥<span>{{$value.discountPrice}}</span></span>
                            <span class="product-original-price">￥<span>{{$value.price}}</span></span>
                        {{else}}
                            <span class="product-price">￥<span>{{$value.price}}</span></span>
                        {{/if}}
                        </div>
                    </div>
                </div>
            </a>
        </li>
    {{/each}}
</script>
<script>
    $(function() {
        $("#fetchMore").bind("click", fetchMoreItem);
    });

    var fetchMoreItem = function() {
        var keyword = $("input[name='keyword']").val(),
        tag = $("input[name='tag']").val(),
        categoryId = $("input[name='categoryId']").val(),
        limit = 10,
        current_page = $('#current_page').val(),
        offset = current_page * limit;

        $.ajax({
            url : 'api/products',
            type : 'GET',
            data: {keyword: keyword, tag: tag, categoryId: categoryId, offset: offset, limit: limit},
            dataType : 'json',
            beforeSend : function() {
                $('#fetchMoreMsg').html('加载中');
                $('#fetchMore').unbind("click");
            },
            success : function(data) {
                if (data.ret == 0) {
                    if(null != data.list && data.list.length > 0) {
                        var html = template('products-template', data);
                        $('.list_body').append(html);
                        $('#current_page').val(++current_page);
                        $('#fetchMoreMsg').html('加载更多');
                    } else {
                        $('#fetchMoreMsg').html('没有了');
                    }
                    $("#fetchMore").bind("click", fetchMoreItem);
                } else {
                    $('#fetchMoreMsg').html('加载失败');
                    setTimeout(function(){
                        $('#fetchMoreMsg').html('加载更多'); 
                        $("#fetchMore").bind("click", fetchMoreItem);
                    }, 2000);
                }
            },
            error : function() {
                $('#fetchMore').html('加载失败');
                setTimeout(function(){
                    $('#fetchMoreMsg').html('加载更多'); 
                    $("#fetchMore").bind("click", fetchMoreItem);
                }, 2000);
            }
        });
    };
</script>
<!-- products end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
