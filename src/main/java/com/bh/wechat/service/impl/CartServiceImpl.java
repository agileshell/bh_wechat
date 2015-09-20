package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.CartProductCreateRequest;
import com.bh.wechat.request.CartProductDeleteRequest;
import com.bh.wechat.request.CartProductListRequest;
import com.bh.wechat.request.CartProductUpdateRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.CartProductListResponse;
import com.bh.wechat.service.CartService;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午3:27:01
 */
@Service
public class CartServiceImpl extends BaseService implements CartService {

    @Override
    public CartProductListResponse getCartProducts(String token) throws BhException {
        CartProductListRequest request = new CartProductListRequest();
        request.setToken(token);

        return httpGatewayTemplate.invoke(CartProductListResponse.class, request);
    }

    @Override
    public BaseResponse addCartProduct(CartProductCreateRequest request) throws BhException {
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

    @Override
    public BaseResponse removeCartProduct(CartProductDeleteRequest request) throws BhException {
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

    @Override
    public BaseResponse updateCartProductQty(CartProductUpdateRequest request) throws BhException {
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }
}
