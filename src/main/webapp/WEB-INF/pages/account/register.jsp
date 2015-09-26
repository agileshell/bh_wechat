<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>注册</title>
    <link rel="stylesheet" href="resources/css/account.css?v=${version}" />
</head>

<header>
    <div class="header-bar">
        <div id="layout_urlblack" data-url="login" class="header-icon-back">
            <span></span>
        </div>
        <div class="header-title">注册</div>
    </div>
</header>

<div class="common-wrapper">
    <div class="main">
        <div class="item item-tips" style="display: none;">
            <div class="err-msg"></div>
        </div>
        <div class="item item-username">
            <input class="txt-input txt-username" type="text" placeholder="请输入用户名" autofocus required="required">
        </div>
        <div class="item item-mobile">
            <input class="txt-input txt-mobile" type="tel" placeholder="请输入手机号码" required="required">
        </div>
        <div class="item item-password">
            <input class="txt-input txt-password" type="password" autocomplete="off" placeholder="请输入密码" required="required">
            <b class="tp-btn btn-off"></b>
        </div>
        <div class="item item-captcha">
            <div class="input-info">
                <input class="txt-input txt-captcha" type="tel" size="11" maxlength="4" autocomplete="off" placeholder="请输入验证码">
                <span id="captcha-img"><img src="captcha" width="63" height="25"></span>
            </div>
        </div>
        <div class="item item-btns">
            <a class="btn-submit btn-disabled" href="javascript:;">注册</a>
        </div>
        <div class="item item-option">
            <span class="left"><a href="home" class="">首页</a></span>
            <span class="right"><a href="login" class="">登录</a></span>
        </div>
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
    (function() {
        var _len_user = 0, _len_mobile = 0, _len_passwd = 0, _len_ckcode = 0;
        $(document).ready(function() {
            hasCookie();
            bindEvents();
        });

        function hasCookie() {
            if (!navigator.cookieEnabled) {
                $('.item-tips').show().children('.err-msg').html('您的手机浏览器不支持或已经禁止使用cookie，无法正常登录，请开启或更换其他浏览器');
            }
        }

        function bindEvents() {
            $('.btn-off').on('click', function() {
                if ($(this).hasClass('btn-on')) {
                    $(this).removeClass('btn-on');
                    $(this).prev().attr('type', 'password');
                } else {
                    $(this).addClass('btn-on');
                    $(this).prev().attr('type', 'text');
                }
            });
            $('#captcha-img').on('click', function() {
                refreshCaptcha();
            });
            _len_user = $('.txt-username').on('input', function() {
                $(this).removeClass('txt-err');
                _len_user = this.value.length;
                enableSubmit();
            }).val().length;
            _len_mobile = $('.txt-mobile').on('input', function() {
                $(this).removeClass('txt-err');
                _len_mobile = this.value.length;
                enableSubmit();
            }).val().length;
            _len_passwd = $('.txt-password').on('input', function() {
                $(this).removeClass('txt-err');
                _len_passwd = this.value.length;
                enableSubmit();
            }).val().length;
            _len_ckcode = $('.txt-captcha').on('input', function() {
                $(this).removeClass('txt-err');
                _len_ckcode = this.value.length;
                enableSubmit();
            }).val().length;
            $('.btn-submit').on('click', function() {
                if (!$(this).hasClass('btn-disabled')) {
                    submitOperate();
                }
            });
        }

        function enableSubmit() {
            if (_len_user && _len_mobile && _len_passwd && _len_ckcode) {
                $('.btn-submit').removeClass('btn-disabled');
            } else {
                $('.btn-submit').addClass('btn-disabled');
            }
        }

        function refreshCaptcha() {
            var url = 'captcha?' + Math.random();
            $('#captcha-img').children('img').attr('src', url);
        }

        function makeParams() {
            var params = {};
            params.userName = $('.txt-username').val();
            params.mobile = $('.txt-mobile').val();
            params.password = $('.txt-password').val();
            params.captcha = $('.txt-captcha').val();
            return params;
        }

        function submitOperate() {
            $('.btn-submit').addClass('btn-disabled').html('注册中');
            $('.item-tips').hide();
            var params = makeParams();
            $.ajax({
                url : 'api/register',
                type : 'POST',
                data : params,
                dataType : 'json',
                success : function(rdata) {
                    $('.btn-submit').removeClass('btn-disabled').html('注册');
                    if (rdata.ret == 0) {
                        window.location.href = 'profile';
                    } else {
                        switch (rdata.ret) {
                        case 2001:
                            $('.txt-mobile').focus().addClass('txt-err');
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        case 2002:
                        case 2003:
                            $('.txt-username').focus().addClass('txt-err');
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        case 5000:
                            $('.txt-captcha').focus().addClass('txt-err');
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        case 2010:
                            pop({
                                msg : '你还未关注公众号，请先关注',
                                btn : '去关注',
                                url : "follow"
                            });
                            break;
                        default:
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        }
                    }
                },
                error : function() {
                    $('.item-tips').show().children('.err-msg').html('服务器开小差，请稍后重试');
                    $('.btn-submit').removeClass('btn-disabled').html('注册');
                }
            });
        }
    }());
</script>