function reduce() {
	var a = parseInt($("#number").val(), 10);
	if (a <= 1) {
		$("#number").val(1)
	} else {
		a--;
		$("#number").val(a)
	}
}

function add(max) {
	var a = parseInt($("#number").val(), 10);
	if (a >= max) {
		$("#number").val(max)
	} else {
		a++;
		$("#number").val(a)
	}
}

function modify(max) {
	var a = parseInt($("#number").val(), 10);
	if ("" == $("#number").val()) {
		$("#number").val(1);
		return
	}
	if (!isNaN(a)) {
		if (1 > a) {
			$("#number").val(1);
			return
		} else if (a > max) {
			$("#number").val(max);
			return
		} else {
			$("#number").val(a);
			return
		}
	} else {
		$("#number").val(1)
	}
}

function hidePop() {
	if ($("#_mask")) {
		$("#_mask").remove()
	}
	$("#popImg").hide()
}

function zoomImgLeft() {
	var a = window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft;
	var c = document.documentElement.clientWidth || document.body.clientWidth;
	var b = a - 150 + (c / 2);
	document.getElementById("popImg").style.left = b + "px"
}

function getLeft() {
	var a = window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft;
	var d = document.documentElement.clientWidth || document.body.clientWidth;
	var c = a - 114 + (d / 2);
	document.getElementById("cart").style.left = c + "px";
}
$(function() {
	getLeft();
	zoomImgLeft();
	$(window).bind("resize", function() {
		getLeft();
		zoomImgLeft()
	})
});

$(function() {
	function g(U) {
		if (false && window.addEventListener) {
			U.addEventListener("touchstart", h, false);
			U.addEventListener("touchmove", y, false);
			U.addEventListener("touchend", I, false)
		} else {
			if (false && window.attachEvent) {
				U.attachEvent("touchstart", h);
				U.attachEvent("touchmove", y);
				U.attachEvent("touchend", I)
			} else {
				U.ontouchstart = h;
				U.ontouchmove = y;
				U.ontouchend = I
			}
		}
	}

	function B(U, Z) {
		var aa = u();
		if (aa == "") {
			return
		}
		var V = aa.split(",");
		if (V.length == 0) {
			return
		}
		if (Z == undefined) {
			var X = '<span class="tbl-cell"><img src="' + U.src + '" seq="' + k + '" width="320" height="292"></span>';
			$("#imgSlider").html(X);
			var X = "";
			for (var W = 0; W < V.length; W++) {
				X = X + '<li class="new-tbl-cell ' + (W == 0 ? "on" : "") + '"><a href="javascript:void(0)"></a></li>'
			}
			if (X != "") {
				$("#imgSliderPage").show();
				$("#imgSliderPage").html(X)
			} else {
				$("#imgSliderPage").hide()
			}
			$("#imgSlider").css("margin-left", "0px");
			var X = '<li class="new-tbl-cell"><a href="javascript:void(0)"><span class="new-shade-img"><img src="' + U.src + '" seq="' + k + '" alt="" width="300" height="300"></span></a></li>';
			$("#bigImgSlider").html(X);
			var X = "";
			for (var W = 0; W < V.length; W++) {
				X = X + '<li class="new-tbl-cell ' + (W == 0 ? "on" : "") + '"><a href="javascript:void(0)"></a></li>'
			}
			if (X != "") {
				$("#bigImgSliderPage").html(X);
				$("#bigImgSliderPage").show()
			} else {
				$("#bigImgSliderPage").hide()
			}
			$("#bigImgSlider").css("margin-left", "0px")
		} else {
			if (Z == 1) {
				if (V.length == 1) {
					return
				}
				var Y = k - 1 < 0 ? V.length - 1 : k - 1;
				$('#imgSlider img[seq="' + Y + '"]').parent("span").siblings().remove();
				var X = '<span class="tbl-cell"><img src="' + G[k].src + '" seq="' + k + '" width="320" height="292"></span>';
				$("#imgSlider").append(X);
				$("#imgSlider").css("left", "0px");
				setTimeout(function() {
					$("#imgSlider").animate({
						left: "-320px"
					}, 200)
				}, 10);
				$("#imgSliderPage").children("li").removeClass("on");
				$("#imgSliderPage").children("li").eq(k).addClass("on");
				$("#imgSliderPage").show();
				$('#bigImgSlider img[seq="' + Y + '"]').parent("span").parent("a").parent("li").siblings().remove();
				var X = '<li class="new-tbl-cell"><a href="javascript:void(0)"><span class="new-shade-img"><img src="' + G[k].src + '" seq="' + k + '" width="300" height="300"></span></a></li>';
				$("#bigImgSlider").append(X);
				$("#bigImgSlider").css("margin-left", "0px");
				setTimeout(function() {
					$("#bigImgSlider").animate({
						"margin-left": "-300px"
					}, 200)
				}, 10);
				$("#bigImgSliderPage").children("li").removeClass("on");
				$("#bigImgSliderPage").children("li").eq(k).addClass("on");
				$("#bigImgSliderPage").show()
			} else {
				if (Z == 0) {
					if (V.length == 1) {
						return
					}
					var Y = k + 1 > V.length - 1 ? 0 : k + 1;
					$('#imgSlider img[seq="' + Y + '"]').parent("span").siblings().remove();
					var X = '<span class="tbl-cell"><img src="' + G[k].src + '" seq="' + k + '" width="320" height="292"></span>';
					$("#imgSlider").prepend(X);
					$("#imgSlider").css("left", "-320px");
					setTimeout(function() {
						$("#imgSlider").animate({
							left: "0px"
						}, 200)
					}, 10);
					$("#imgSliderPage").children("li").removeClass("on");
					$("#imgSliderPage").children("li").eq(k).addClass("on");
					$("#imgSliderPage").show();
					$('#bigImgSlider img[seq="' + Y + '"]').parent("span").parent("a").parent("li").siblings().remove();
					var X = '<li class="new-tbl-cell"><a href="javascript:void(0)"><span class="new-shade-img"><img src="' + G[k].src + '" seq="' + k + '" width="300" height="300"></span></a></li>';
					$("#bigImgSlider").prepend(X);
					$("#bigImgSlider").css("margin-left", "-300px");
					setTimeout(function() {
						$("#bigImgSlider").animate({
							"margin-left": "0px"
						}, 200)
					}, 10);
					$("#bigImgSliderPage").children("li").removeClass("on");
					$("#bigImgSliderPage").children("li").eq(k).addClass("on");
					$("#bigImgSliderPage").show()
				}
			}
		}
		if (U) {}
	}

	function p(U) {
		if (U && U.ware && U.ware.images && U.ware.images.length > 0) {
			var V = "";
			$.each(U.ware.images, function(X, W) {
				if (W && W.newpath) {
					V = V + W.newpath.replace("/n4/", "/n1/") + ","
				}
			});
			$("#imgs").val(V);
			G = new Array(u().split(",").length);
			k = 0;
			E();
			B(G[k])
		}
	}

	function u() {
		var U = $.trim($("#imgs").val());
		if (U.substring(U.length - 1) == ",") {
			U = U.substring(0, U.length - 1)
		}
		return U
	}
	var G = new Array(u().split(",").length);
	var k = 0;
	var c = 1;

	function L(W, X, U) {
		var V = new Image();
		V.src = W;
		V.width = X;
		V.height = U;
		V.ontouchstart = h;
		V.ontouchmove = y;
		V.ontouchend = I;
		return V
	}
	$(function() {
		E()
	});

	function E() {
		var X = u();
		if (X != "") {
			var W = X.split(",");
			if (!G[k]) {
				G[k] = (L(W[k], 200, 200))
			}
			var V;
			for (var U = 0; U < c; U++) {
				V = k + (U + 1);
				if (V < W.length) {
					if (!G[V]) {
						G[V] = (L(W[V], 200, 200))
					}
				} else {
					V = V - W.length;
					if (!G[V]) {
						G[V] = (L(W[V], 200, 200))
					}
				}
				V = k - (U + 1);
				if (V < 0) {
					V = W.length + V;
					if (!G[V]) {
						G[V] = (L(W[V], 200, 200))
					}
				} else {
					if (!G[V]) {
						G[V] = (L(W[V], 200, 200))
					}
				}
			}
		}
	}

	function e() {
		var V = u();
		k++;
		var U = V.split(",");
		if (k > U.length - 1) {
			k = 0
		}
		E();
		B(G[k], 1)
	}

	function N() {
		var V = u();
		k--;
		var U = V.split(",");
		if (k < 0) {
			k = U.length - 1
		}
		E();
		B(G[k], 0)
	}
	var z, w, m, l, b, a;

	function h(U) {
		var V = U.touches[0];
		z = V.pageX;
		w = V.pageY
	}

	function y(U) {
		var V = U.touches[0];
		m = V.pageX;
		l = V.pageY;
		b = Math.abs(z - m);
		a = Math.abs(w - l);
		if (b > a) {
			U.preventDefault()
		}
	}

	function I(U) {
		if (b > a) {
			if (z > m) {
				e()
			} else {
				N()
			}
		}
		z = 0, w = 0, m = 0, l = 0, b = 0, a = 0
	}
	if (document.getElementById("imgSlider")) {
		g(document.getElementById("imgSlider"))
	}
	if (document.getElementById("popImg")) {
		g(document.getElementById("popImg"))
	}

	function O() {
		if ($("#_mask")) {
			$("#_mask").remove()
		}
		var U = ((document.body || document.documentElement).clientHeight + 20) + "px";
		var W = "100%";
		var V = document.createElement("div");
		V.setAttribute("id", "_mask");
		V.setAttribute("style", "position:absolute;left:0px;top:0px;background-color:rgb(0, 0, 0);filter:alpha(opacity=90);opacity: 0.9;width:" + W + ";height:" + U + ";z-index:8889;");
		(document.body || document.documentElement).appendChild(V);
		$("#_mask").click(function() {
			$("#cart").hide();
			if ($("#popImg")) {
				$("#popImg").hide()
			}
			if ($("#_mask")) {
				$("#_mask").hide()
			}
		});
		var Y = 400;
		var X = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
		var Z = document.documentElement.clientHeight || document.body.clientHeight;
		document.getElementById("popImg").style.top = ((Z - Y) / 2 + X) + "px";
		$("#popImg").show()
	}
	$("#_zoom").click(function() {
		O()
	})
});