$(document).ready(function() {
	// 显示菜单
	if ($("#layout_menuKey")) {
		$("#layout_menuKey").click(function() {
			if ($("#layout_menuBar").css("display") == "none") {
				$("#layout_menuBar").show();
			} else {
				$("#layout_menuBar").hide();
			}
		})
	}

	// 回到顶部
	$(window).scroll(function() {
		if ($(window).scrollTop() > 10) {
			$("#back_to_top").fadeIn(500);
		} else {
			$("#back_to_top").fadeOut(500);
		}
	});
	$("#back-top").click(function() {
		$('body,html').animate({
			scrollTop : 0
		}, 1000);
		return false;
	});

	// 回退
	$("#layout_urlblack").on("click", function() {
		pageBack();
	});

	// 首页
	$("#go-home").on("click", function() {
		window.location.href="home"; 
	});
});

function pop(info) {
	var $total = $('#pop-choose');
	$('.pop-msg', $total).html(info.msg);
	$('.btn-continue', $total).html(info.btn).attr('href', info.url);
	$total.css('display', 'block');
}

function pageBack() {
	var a = window.location.href;
	if (/#top/.test(a)) {
		window.history.go(-2);
		window.location.load(a);
	} else {
		window.history.back();
		window.location.load(a);
	}
}

function addLocalStorage(d) {
	if (null != d) {
		var a = window.localStorage.getItem("viewItem");
		var c;
		if (a != null) {
			var e = new Array();
			e = a.split(",");
			for (var b = 0; b < e.length; b++) {
				if (d == e[b]) {
					c = true
				}
			}
			if (!c) {
				e.push(d)
			}
			if (e.length > 20) {
				e.shift();
				window.localStorage.setItem("viewItem", e)
			} else {
				window.localStorage.setItem("viewItem", e)
			}
		} else {
			window.localStorage.setItem("viewItem", d)
		}
	}
}