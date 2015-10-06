var cartPriceEl = $("#cart_price"), cartPointEl = $("#cart_point"), checkedNumEl = $("#checked_num"), shipFeeEl = $("#ship_fee");
var selectedCartProduct = [], cartCount = $("#cartCount").val(), cartPrice = 0.0, cartPoint = 0, checkedNum = 0;
var realShipFee = 0;

function reduceWare(id) {
    var idEl = $("#qty" + id);
    var a = parseInt(idEl.val(), 10);
    var inventory = parseInt($("#inventory" + id).val(), 10);
    if (a > 1 && inventory > 0) {
        a--;
        if (a > inventory) {
             a = inventory;
        }
        idEl.val(a);
        $("#lastQty" + id).val(a);

        updateCartProductQty(id, a);

        modifyDisplayData(id, -1);

        $('#' + id).addClass("displayed");
        $('#cart-checkbox-' + id).addClass("displayed");
        $('#cart-checkbox-' + id).removeClass("undisplay");
    }
}

function addWare(id) {
    var idEl = $("#qty" + id);
    var a = parseInt(idEl.val(), 10);
    var inventory = parseInt($("#inventory" + id).val(), 10);
    if (a == inventory) {
         return;
    }
    if (a > 0 && inventory > 0) {
        a++;
        if (a > inventory) {
             a = inventory;
        }
        idEl.val(a);

        var lastQty = parseInt($("#lastQty" + id).val(), 10);
        modifyDisplayData(id, a - lastQty);

        $('#' + id).addClass("displayed");
        $('#cart-checkbox-' + id).addClass("displayed");
        $('#cart-checkbox-' + id).removeClass("undisplay");

        $("#lastQty" + id).val(a);

        updateCartProductQty(id, a);
    }
}

function modifyWare(id) {
    var inventory = parseInt($("#inventory" + id).val(), 10);
    if (inventory <= 0) {
        return;
    }

    var idEl = $("#qty" + id);
    var a = parseInt(idEl.val(), 10);
    if ("" == idEl.val()) {
        a = 1;
    } else if (isNaN(a)) {
        a = 1;
    } else {
        if (1 > a) {
            a = 1;
        } else if (a > inventory){
            a = inventory;
        }
    }
    idEl.val(a);

    var lastQty = parseInt($("#lastQty" + id).val(), 10);

    modifyDisplayData(id, a - lastQty);
    $('#' + id).addClass("displayed");
    $('#cart-checkbox-' + id).addClass("displayed");
    $('#cart-checkbox-' + id).removeClass("undisplay");

    $("#lastQty" + id).val(a);

    updateCartProductQty(id, a);
}

function modifyDisplayData(id, qty) {
    var position = $.inArray(id, selectedCartProduct);
    if (position >= 0) {
        cartPrice = parseFloat(cartPrice) + parseFloat($("#price" + id).val())
                * qty;
        cartPoint = parseInt(cartPoint) + parseInt($("#point" + id).val())
                * qty;
        checkedNum = parseInt(checkedNum) + qty;

        if (cartPrice >= 299 && realShipFee > 0) {
        	cartPrice -= 15.00;
        	realShipFee = 0;
        	shipFeeEl.html('');
        } else if (cartPrice == 0) {
        	realShipFee = 0;
        	shipFeeEl.html('');
        }

        if(realShipFee <= 0) {
            if (cartPrice >= 299) {
            	shipFeeEl.html('(免运费)');
            	realShipFee = 0;
            } else if (cartPrice > 0) {
            	cartPrice += 15.00;
            	shipFeeEl.html('(含运费15元)');
            	realShipFee = 15.00;
            }
        }

        cartPriceEl.html(cartPrice.toFixed(2));
        cartPointEl.html(cartPoint);
        checkedNumEl.html(checkedNum);
    }
}

function modifyDisplayData2(id, qty) {
    cartPrice = parseFloat(cartPrice) + parseFloat($("#price" + id).val()) * qty;
    cartPoint = parseInt(cartPoint) + parseInt($("#point" + id).val()) * qty;
    checkedNum = parseInt(checkedNum) + qty;

    if (cartPrice >= 299 && realShipFee > 0) {
    	cartPrice -= 15.00;
    	realShipFee = 0;
    	shipFeeEl.html('');
    } else if (cartPrice == 0) {
    	realShipFee = 0;
    	shipFeeEl.html('');
    }

    if(realShipFee <= 0) {
        if (cartPrice >= 299) {
        	shipFeeEl.html('(免运费)');
        	realShipFee = 0;
        } else if (cartPrice > 0) {
        	cartPrice += 15.00;
        	shipFeeEl.html('(含运费15元)');
        	realShipFee = 15.00;
        }
    }

    cartPriceEl.html(cartPrice.toFixed(2));
    cartPointEl.html(cartPoint);
    checkedNumEl.html(checkedNum);
}

function changeSelected(id) {
    var qty = parseInt($("#qty" + id).val());
    var inventory = parseInt($("#inventory" + id).val(), 10);
    if (inventory >= qty) {
        var position = $.inArray(id, selectedCartProduct);
        if (position >= 0) {
            selectedCartProduct.splice(position, 1);
            modifyDisplayData2(id, -qty);
            $("#" + id + " .cart-checkbox").removeClass("checked");
        } else {
            selectedCartProduct.push(id);
            modifyDisplayData2(id, qty);
            $("#" + id + " .cart-checkbox").addClass("checked");
        }

        if (selectedCartProduct.length == cartCount) {
            $("#checkAll").addClass("checked");
        } else {
            $("#checkAll").removeClass("checked");
        }
    }
}

function checkAllHandler() {
     var flag = $("#checkAll").hasClass("checked");
    if (flag) {
    	realShipFee = 0;
        $(".cart-checkbox").removeClass("checked");
        selectedCartProduct = [];

        cartPrice = 0.0;
        cartPoint = 0;
        checkedNum = 0;

        cartPriceEl.html(0.0);
        cartPointEl.html(0);
        checkedNumEl.html(0);
    } else {
        $("#checkAll").addClass("checked");
        selectedCartProduct = [];
        $(".cart-checkbox.displayed").addClass("checked");

        cartPrice = 0.0;
        cartPoint = 0;
        checkedNum = 0;

        $("#notEmptyCart li.displayed").each(function(index, element) {
            var id = $(this).attr("id"), qty = parseInt($("#qty" + id).val());
            selectedCartProduct.push(parseInt(id));
            modifyDisplayData2(id, qty);
        });
    }
}

function deleteWare(id) {
    if (confirm("确定要删除吗？")) {
        $.ajax({
            url : 'api/removeProductFromCart',
            type : 'POST',
            data : {
                cartProductId : id
            },
            dataType : 'json',
            success : function(rdata) {
                if (rdata.ret == 0) {
                    window.location.href = 'myCart';
                } else {
                    switch (rdata.ret) {
                    case 1000:
                        alert(rdata.msg);
                        break;
                    case 1001:
                        pop({
                            msg : '您的登录凭证已过期，请重新登录',
                            btn : '登录',
                            url : 'login'
                        });
                        break;
                    default:
                        alert(rdata.msg);
                        break;
                    }
                }
            },
            error : function() {
                alert("删除失败");
            }
        });
    }
}

function updateCartProductQty(cartProductId, qty) {
    $.ajax({
        url : 'api/updateCartProductQty',
        type : 'POST',
        data : {
            cartProductId : cartProductId,
            qty : qty
        },
        dataType : 'json',
        success : function(rdata) {
            if (rdata.ret != 0) {
                switch (rdata.ret) {
                case 1000:
                    alert(rdata.msg);
                    break;
                case 1001:
                    pop({
                        msg : '您的登录凭证已过期，请重新登录',
                        btn : '登录',
                        url : 'login'
                    });
                    break;
                default:
                    alert(rdata.msg);
                    break;
                }
            }
        },
        error : function() {
            alert("修改失败");
        }
    });
}

function checkout() {
    if (selectedCartProduct.length == 0) {
        alert("请先选择一个商品");
    } else {
        var cartProducts = [];
        for (var i = 0, j = selectedCartProduct.length; i < j; i++) {
            var product = {};
            var id = selectedCartProduct[i];
            product.cartProductId = id;
            product.productId = parseInt($("#productId" + id).val());
            product.name = $("#name" + id).val();
            product.coverImg = $("#coverImg" + id).val();
            product.price = parseFloat($("#price" + id).val());
            product.guige = $("#guige" + id).text();
            product.qty = parseInt($("#qty" + id).val());
            product.baodouPercent = parseFloat($("#baodouPercent" + id).val());

            cartProducts.push(product);
        }
        var param = {};
        param.cartProducts = cartProducts;

        window.location.href = "checkoutOrder?products="
                + JSON.stringify(param);
    }
}
