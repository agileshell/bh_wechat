package com.bh.wechat.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.CartProductCreateRequest;
import com.bh.wechat.request.CartProductDeleteRequest;
import com.bh.wechat.request.CartProductUpdateRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.CartProduct;
import com.bh.wechat.response.CartProductListResponse;

@Controller
public class CartController extends BaseController {

    @RequestMapping(value = "/myCart", method = RequestMethod.GET)
    public String listCartProducts(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("myCart");
            }
        }

        if (isLogined) {
            CartProductListResponse cartResponse = cartService.getCartProducts(getToken());

            if (cartResponse.isSuccess()) {
                model.addAttribute("cartProducts", null != cartResponse.getList() ? cartResponse.getList()
                        : new ArrayList<CartProduct>());
            } else {
                model.addAttribute("cartProducts", new ArrayList<CartProduct>());
            }

            return "cart/list";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/addProductIntoCart", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addProductIntoCart(CartProductCreateRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse baseResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            baseResponse = cartService.addCartProduct(requestData);
        } else {
            baseResponse = new BaseResponse();
            baseResponse.setRet(1001);
        }

        return baseResponse;
    }

    @RequestMapping(value = "/api/removeProductFromCart", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse removeProductFromCart(CartProductDeleteRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse baseResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            baseResponse = cartService.removeCartProduct(requestData);
        } else {
            baseResponse = new BaseResponse();
            baseResponse.setRet(1001);
        }

        return baseResponse;
    }

    @RequestMapping(value = "/api/updateCartProductQty", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateCartProductQty(CartProductUpdateRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse baseResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            baseResponse = cartService.updateCartProductQty(requestData);
        } else {
            baseResponse = new BaseResponse();
            baseResponse.setRet(1001);
        }

        return baseResponse;
    }
}
