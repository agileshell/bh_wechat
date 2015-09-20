package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.GatewayProxyRequest;
import com.bh.wechat.request.AddressCreateRequest;
import com.bh.wechat.request.AddressDetailRequest;
import com.bh.wechat.request.AddressUpdateRequest;
import com.bh.wechat.request.LocationRequest;
import com.bh.wechat.response.AddressDetailResponse;
import com.bh.wechat.response.AddressListResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.LocationResponse;
import com.bh.wechat.service.AddressService;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午2:29:32
 */
@Service
public class AddressServiceImpl extends BaseService implements AddressService {

    @Override
    public AddressListResponse getAddressList(String token) throws BhException {
        GatewayProxyRequest<AddressListResponse> request = new GatewayProxyRequest<AddressListResponse>()
                .parameter("token", token).responseType(AddressListResponse.class).apiUri(ApiUri.GET_ADDRESSES);
        return httpGatewayTemplate.invoke(request);
    }

    @Override
    public AddressDetailResponse createAddress(AddressCreateRequest request) throws BhException {
        return httpGatewayTemplate.invoke(AddressDetailResponse.class, request);
    }

    @Override
    public AddressDetailResponse getAddressDetail(AddressDetailRequest request) throws BhException {
        return httpGatewayTemplate.invoke(AddressDetailResponse.class, request);
    }

    @Override
    public AddressDetailResponse updateAddress(AddressUpdateRequest request) throws BhException {
        return httpGatewayTemplate.invoke(AddressDetailResponse.class, request);
    }

    @Override
    public BaseResponse deleteAddress(int addressId, String token) throws BhException {
        GatewayProxyRequest<BaseResponse> request = new GatewayProxyRequest<BaseResponse>().parameter("token", token)
                .uriVariable("addressId", addressId).responseType(BaseResponse.class).apiUri(ApiUri.DELETE_ADDRESS);
        return httpGatewayTemplate.invoke(request);
    }

    @Override
    public BaseResponse setDefaultAddress(int addressId, String token) throws BhException {
        GatewayProxyRequest<BaseResponse> request = new GatewayProxyRequest<BaseResponse>().parameter("token", token)
                .uriVariable("addressId", addressId).responseType(BaseResponse.class)
                .apiUri(ApiUri.SET_DEFAULT_ADDRESS);
        return httpGatewayTemplate.invoke(request);
    }

    @Override
    public AddressDetailResponse getDefaultAddress(String token) throws BhException {
        GatewayProxyRequest<AddressDetailResponse> request = new GatewayProxyRequest<AddressDetailResponse>()
                .parameter("token", token).responseType(AddressDetailResponse.class).apiUri(ApiUri.GET_DEFAULT_ADDRESS);
        return httpGatewayTemplate.invoke(request);
    }

    @Override
    public LocationResponse getLocations(LocationRequest request) throws BhException {
        return httpGatewayTemplate.invoke(LocationResponse.class, request);
    }
}
