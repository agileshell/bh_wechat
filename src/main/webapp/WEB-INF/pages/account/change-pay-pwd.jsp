<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>修改支付密码</title>
    <link rel="stylesheet" href="resources/css/account.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="修改支付密码" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div class="common-wrapper">
    <div class="main">
        <div class="item item-tips" style="display: none;">
            <div class="err-msg"></div>
        </div>
        <div class="item item-password">
            <input class="txt-input txt-password" type="password" id="oldPassword" autocomplete="off" placeholder="原始密码" required="required">
            <b class="tp-btn btn-off"></b>
        </div>
        <div class="item item-password">
            <input class="txt-input txt-password" type="password" id="password" autocomplete="off" placeholder="新密码" required="required">
        </div>
        <div class="item item-password">
            <input class="txt-input txt-password" type="password" id="confirmPassword" autocomplete="off" placeholder="确认密码" required="required">
        </div>
        <div class="item item-captcha">
            <div class="input-info">
                <input class="txt-input txt-captcha" type="tel" size="11" maxlength="4" id="captcha" autocomplete="off" placeholder="验证码"> <span id="captcha-img"><img src="captcha" width="63" height="25"></span>
            </div>
        </div>
        <div class="item item-btns">
            <a class="btn-submit btn-disabled" href="javascript:;">确认</a>
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
        var _len_old_pwd = 0, _len_new_pwd = 0, _len_confirm_pwd = 0, _len_ckcode = 0, _newPwd_eq_confirmPwd = false, _newPwd_noteq_oldPwd = true, _old_pwd = '', _new_pwd = '', _confirm_pwd = '';
        $(document).ready(function() {
            bindEvents();
        });

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
            _len_old_pwd = $('#oldPassword').on('input', function() {
                $(this).removeClass('txt-err');
                _len_old_pwd = this.value.length;
                _old_pwd = this.value;
                if (_len_new_pwd > 0) {
                    if (_new_pwd == _old_pwd) {
                        $('.item-tips').show().children('.err-msg').html("新密码不能等于原始密码");
                        _newPwd_noteq_oldPwd = false;
                    }
                }
                enableSubmit();
            }).val().length;
            _len_new_pwd = $('#password').on('input', function() {
                $(this).removeClass('txt-err');
                _len_new_pwd = this.value.length;
                _new_pwd = this.value;
                if (_len_confirm_pwd > 0) {
                    if (_confirm_pwd == _new_pwd) {
                        $('.item-tips').show().children('.err-msg').html("");
                        _newPwd_eq_confirmPwd = true;
                    } else {
                        $('.item-tips').show().children('.err-msg').html("新密码和确认密码不一致");
                        _newPwd_eq_confirmPwd = false;
                    }
                }
                if (_len_old_pwd > 0) {
                    if (_new_pwd == _old_pwd) {
                        $('.item-tips').show().children('.err-msg').html("新密码不能和原始密码相同");
                        _newPwd_noteq_oldPwd = false;
                    }
                }
                enableSubmit();
            }).val().length;
            _len_confirm_pwd = $('#confirmPassword').on('input', function() {
                $(this).removeClass('txt-err');
                _len_confirm_pwd = this.value.length;
                _confirm_pwd = this.value;
                if (_confirm_pwd == _new_pwd) {
                    $('.item-tips').show().children('.err-msg').html("");
                    _newPwd_eq_confirmPwd = true;
                } else {
                    $('.item-tips').show().children('.err-msg').html("新密码和确认密码不一致");
                    _newPwd_eq_confirmPwd = false;
                }
                enableSubmit();
            }).val().length;
            _len_ckcode = $('#captcha').on('input', function() {
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
            if (_len_old_pwd && _len_new_pwd && _len_confirm_pwd && _len_ckcode && _newPwd_eq_confirmPwd && _newPwd_noteq_oldPwd) {
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
            params.oldPassword = $('#oldPassword').val();
            params.password = $('#password').val();
            params.captcha = $('.txt-captcha').val();
            return params;
        }

        function submitOperate() {
            $('.btn-submit').addClass('btn-disabled').html('提交中');
            $('.item-tips').hide();
            var params = makeParams();
            $.ajax({
                url : 'api/changePayPassword',
                type : 'POST',
                data : params,
                dataType : 'json',
                success : function(rdata) {
                    $('.btn-submit').removeClass('btn-disabled').html('提交');
                    if (rdata.ret == 0) {
                        pop({
                            msg : '修改支付密码成功',
                            btn : '我的账户',
                            url : 'profile'
                        });
                    } else {
                        switch (rdata.ret) {
                        case 1001:
                            pop({
                                msg : '您的登录凭证已过期，请重新登录',
                                btn : '登录',
                                url : 'login'
                            });
                            break;
                        case 2008:
                            $('#oldPassword').focus().addClass('txt-err');
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        case 5000:
                            $('#captcha').focus().addClass('txt-err');
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        default:
                            $('.item-tips').show().children('.err-msg').html(rdata.msg);
                            break;
                        }
                    }
                },
                error : function() {
                    $('.item-tips').show().children('.err-msg').html('服务器开小差，请稍后重试');
                    $('.btn-submit').removeClass('btn-disabled').html('提交');
                }
            });
        }
    }());
</script>