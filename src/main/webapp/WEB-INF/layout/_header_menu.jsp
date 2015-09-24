<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
    <div class="header-bar">
        <div id="layout_urlblack" data-url="${back_url}" class="header-icon-back">
            <span>返回</span>
        </div>
        <div class="header-title">${header_name}</div>
        <div class="header-icon-shortcut" id="layout_menuKey">
            <span>菜单</span>
        </div>
    </div>

    <ul id="layout_menuBar" class="header-shortcut" style="display: none;">
        <li class="${current_menu == 'home' ? 'current' : ''}">
            <a href="home"> <span class="shortcut-home"></span> <strong>首页</strong></a>
        </li>
        <li class="${current_menu == 'categories' ? 'current' : ''}">
            <a href="categories"> <span class="shortcut-categories"></span> <strong>分类搜索</strong></a>
        </li>
        <li class="${current_menu == 'cart' ? 'current' : ''}">
            <a href="myCart"> <span class="shortcut-cart"></span> <strong>购物车</strong></a>
        </li>
        <li class="${current_menu == 'my-account' ? 'current' : ''}">
            <a href="profile"> <span class="shortcut-my-account"></span> <strong>我的账户</strong></a>
        </li>
    </ul>
</header>