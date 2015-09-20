package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.ProductListRequest;
import com.bh.wechat.response.CategoryListResponse;
import com.bh.wechat.response.ProductDetailResponse;
import com.bh.wechat.response.ProductListResponse;

public interface ProductService {

    ProductListResponse listProducts(ProductListRequest productListRequest) throws BhException;

    ProductDetailResponse getProductDetail(int productId) throws BhException;

    CategoryListResponse getCategoryList() throws BhException;
}
