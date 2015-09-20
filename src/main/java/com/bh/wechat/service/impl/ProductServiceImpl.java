package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.GatewayProxyRequest;
import com.bh.wechat.request.ProductListRequest;
import com.bh.wechat.response.CategoryListResponse;
import com.bh.wechat.response.ProductDetailResponse;
import com.bh.wechat.response.ProductListResponse;
import com.bh.wechat.service.ProductService;

@Service
public class ProductServiceImpl extends BaseService implements ProductService {

    public ProductListResponse listProducts(ProductListRequest productListRequest) throws BhException {
        return httpGatewayTemplate.invoke(ProductListResponse.class, productListRequest);
    }

    public ProductDetailResponse getProductDetail(int productId) throws BhException {
        GatewayProxyRequest<ProductDetailResponse> req =
                new GatewayProxyRequest<ProductDetailResponse>().responseType(ProductDetailResponse.class)
                        .apiUri(ApiUri.GET_PRODUCT_DETAIL).uriVariable("productId", productId);
        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public CategoryListResponse getCategoryList() throws BhException {
        GatewayProxyRequest<CategoryListResponse> req =
                new GatewayProxyRequest<CategoryListResponse>().responseType(CategoryListResponse.class).apiUri(
                        ApiUri.GET_CATEGORIES);
        return httpGatewayTemplate.invoke(req);
    }
}
