package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.CartProductCreateRequest;
import com.bh.wechat.request.CartProductDeleteRequest;
import com.bh.wechat.request.CartProductUpdateRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.CartProductListResponse;

public interface CartService {

    /**
     * 获取购物车商品列表
     * 
     * @return
     * @throws BhException
     */
    CartProductListResponse getCartProducts(String token) throws BhException;

    /**
     * 把商品加入购物车
     * 
     * @param request
     * @return
     * @throws BhException
     */
    BaseResponse addCartProduct(CartProductCreateRequest request) throws BhException;

    /**
     * 从购物车中移除商品
     * 
     * @param request
     * @return
     * @throws BhException
     */
    BaseResponse removeCartProduct(CartProductDeleteRequest request) throws BhException;

    /**
     * 修改购物车中商品的数量
     * 
     * @param request
     * @return
     * @throws BhException
     */
    BaseResponse updateCartProductQty(CartProductUpdateRequest request) throws BhException;
}
