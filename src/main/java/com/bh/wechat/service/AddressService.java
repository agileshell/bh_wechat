package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.AddressCreateRequest;
import com.bh.wechat.request.AddressDetailRequest;
import com.bh.wechat.request.AddressUpdateRequest;
import com.bh.wechat.request.LocationRequest;
import com.bh.wechat.response.AddressDetailResponse;
import com.bh.wechat.response.AddressListResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.LocationResponse;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月6日 下午2:26:40
 */
public interface AddressService {
    AddressListResponse getAddressList(String token) throws BhException;

    AddressDetailResponse createAddress(AddressCreateRequest request) throws BhException;

    AddressDetailResponse getAddressDetail(AddressDetailRequest request) throws BhException;

    AddressDetailResponse updateAddress(AddressUpdateRequest request) throws BhException;

    BaseResponse deleteAddress(int addressId, String token) throws BhException;

    BaseResponse setDefaultAddress(int addressId, String token) throws BhException;

    AddressDetailResponse getDefaultAddress(String token) throws BhException;

    LocationResponse getLocations(LocationRequest request) throws BhException;
}
