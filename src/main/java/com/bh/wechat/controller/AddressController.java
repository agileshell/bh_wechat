package com.bh.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.AddressCreateRequest;
import com.bh.wechat.request.AddressDetailRequest;
import com.bh.wechat.request.AddressUpdateRequest;
import com.bh.wechat.request.LocationRequest;
import com.bh.wechat.response.Address;
import com.bh.wechat.response.AddressDetailResponse;
import com.bh.wechat.response.AddressListResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.Location;
import com.bh.wechat.response.LocationResponse;

@Controller
public class AddressController extends BaseController {

    @RequestMapping(value = "/addresses", method = RequestMethod.GET)
    public String addressListView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("addresses");
            }
        }

        if (isLogined) {
            AddressListResponse response = addressService.getAddressList(getToken());
            if (response.isSuccess()) {
                model.addAttribute("addresses", null != response.getList() ? response.getList()
                        : new ArrayList<Address>());
            } else {
                model.addAttribute("addresses", new ArrayList<Address>());
            }

            return "address/list";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/createAddress", method = RequestMethod.GET)
    public String createAddressView(@RequestParam(required = true, defaultValue = "addresses") String redirectURL,
            Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("createAddress");
            }
        }

        if (isLogined) {
            model.addAttribute("firstLocations", getLocations(0));
            model.addAttribute("redirectURL", redirectURL);

            return "address/create";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/createAddress", method = RequestMethod.POST)
    @ResponseBody
    public AddressDetailResponse createAddress(AddressCreateRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        AddressDetailResponse addressResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            addressResponse = addressService.createAddress(requestData);
        } else {
            addressResponse = new AddressDetailResponse();
            addressResponse.setRet(1001);
        }

        return addressResponse;
    }

    @RequestMapping(value = "/updateAddress/{addressId}", method = RequestMethod.GET)
    public String updateAddressView(@PathVariable int addressId,
            @RequestParam(required = true, defaultValue = "addresses") String redirectURL, Model model)
            throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if (isLogined) {
            model.addAttribute("firstLocations", getLocations(0));

            AddressDetailRequest request = new AddressDetailRequest();
            request.setAddressId(addressId);
            request.setToken(getToken());
            model.addAttribute("address", addressService.getAddressDetail(request));
            model.addAttribute("redirectURL", redirectURL);

            return "address/update";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/updateAddress", method = RequestMethod.POST)
    @ResponseBody
    public AddressDetailResponse updateAddress(AddressUpdateRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        AddressDetailResponse addressResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            addressResponse = addressService.updateAddress(requestData);
        } else {
            addressResponse = new AddressDetailResponse();
            addressResponse.setRet(1001);
        }

        return addressResponse;
    }

    @RequestMapping(value = "/api/deleteAddress", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse deleteAddress(@RequestParam(required = true) int addressId) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse baseResponse = null;
        if (isLogined) {
            baseResponse = addressService.deleteAddress(addressId, getToken());
        } else {
            baseResponse = new BaseResponse();
            baseResponse.setRet(1001);
        }

        return baseResponse;
    }

    @RequestMapping(value = "/api/setDefaultAddress", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse setDefaultAddress(@RequestParam(required = true) int addressId) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse baseResponse = null;
        if (isLogined) {
            baseResponse = addressService.setDefaultAddress(addressId, getToken());
        } else {
            baseResponse = new BaseResponse();
            baseResponse.setRet(1001);
        }

        return baseResponse;
    }

    @RequestMapping(value = "/api/locations/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Location> listLocations(@PathVariable int parentId) throws BhException {
        return getLocations(parentId);
    }

    private List<Location> getLocations(int parentId) throws BhException {
        LocationRequest request = new LocationRequest();
        request.setParentId(parentId);
        LocationResponse locationResponse = addressService.getLocations(request);
        if (locationResponse.isSuccess()) {
            return locationResponse.getList();
        } else {
            return new ArrayList<Location>();
        }
    }
}
