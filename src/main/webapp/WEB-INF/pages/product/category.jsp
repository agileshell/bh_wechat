<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>分类搜索</title>
    <link rel="stylesheet" type="text/css" href="resources/css/category.css?v=${version}" />
    <script type="text/javascript" src="resources/js/template.js?v=${version}"></script>
</head>

<!-- header start -->
<c:set var="header_name" value="分类搜索" />
<c:set var="current_menu" value="categories" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- categories start -->
<div id="categoryBody" class="category-viewport" style="display: block;">
    <div id="rootList" class="category-tab">
        <ul id="parentCategories">
        </ul>
    </div>
    <div class="category-content">
        <div id="branchScroll" style="overflow: hidden;" class="category-content-wrapper">
            <div id="childCategories">
            </div>
        </div>
    </div>
</div>
<script id="parent-categories-template" type="text/html">
    <li id="category{{categoryId}}" class="{{if isShow == true}}cur{{/if}}"><a href="javascript:void(0);">{{name}}</a></li>
</script>
<script id="child-categories-template" type="text/html">
{{each list as item}}
<div id="category{{item.categoryId}}" class="category-div {{if isShow == true}}cur{{/if}} category{{parentId}}">
    {{if item.child.length > 0}}
        <h4>{{item.name}}</h4>
        <ul class="category-style-1">
        {{each item.child as val}}
            <li><a id="category{{val.categoryId}}" href="products?categoryId={{val.categoryId}}"><span>{{val.name}}</span></a></li>
        {{/each}}
        </ul>
    {{/if}}
</div>
{{/each}}
</script>
<script>
    $(function() {
        getCategories();

        $(".btn-loadfail").on("click", function() {
            getCategories();
        });
        $("#parentCategories").on("click", "li", function() {
            $("#parentCategories li").removeClass("cur");
            $(this).addClass("cur");

            $("#childCategories .category-div").removeClass("cur");
            $('#childCategories .' + $(this).attr("id")).addClass("cur");
        });
    });
    var getCategories = function() {
        $.ajax({
            url : 'api/categories',
            type : 'GET',
            dataType : 'json',
            success : function(data) {
                if (data.ret == 0) {
                    var items = data.list, item = {}, child = {}, parent_categories_html = '', child_categories_template = '';
                    for (var i = 0, len = items.length; i < len; i++) {
                        item.categoryId = items[i].categoryId;
                        item.name = items[i].name;
                        item.isShow = (i == 0) ? true : false;
                        parent_categories_html += template('parent-categories-template', item);

                        child.parentId = item.categoryId;
                        child.isShow = (i == 0) ? true : false;
                        child.list = items[i].child;
                        child_categories_template += template('child-categories-template', child);
                    }
                    $('#parentCategories').append(parent_categories_html);
                    $('#childCategories').append(child_categories_template);
                }
            }
        });
    }
</script>
<!-- categories end -->