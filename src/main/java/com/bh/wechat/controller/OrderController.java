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
import com.bh.wechat.model.CartProductListModel;
import com.bh.wechat.model.CartProductModel;
import com.bh.wechat.model.GuigeModel;
import com.bh.wechat.request.AddressDetailRequest;
import com.bh.wechat.request.OrderCancelRequest;
import com.bh.wechat.request.OrderCreateDirectlyRequest;
import com.bh.wechat.request.OrderCreateRequest;
import com.bh.wechat.request.OrderDetailRequest;
import com.bh.wechat.request.OrderListRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.Address;
import com.bh.wechat.response.AddressDetailResponse;
import com.bh.wechat.response.AddressListResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.Order;
import com.bh.wechat.response.OrderDetailResponse;
import com.bh.wechat.response.OrderListResponse;
import com.bh.wechat.response.ProductDetailResponse;
import com.bh.wechat.util.JacksonUtils;

@Controller
public class OrderController extends BaseController {

    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    @ResponseBody
    public OrderListResponse listProducts(OrderListRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        OrderListResponse orderListResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            orderListResponse = orderService.listOrders(requestData);
        } else {
            orderListResponse = new OrderListResponse();
            orderListResponse.setRet(1001);
        }

        return orderListResponse;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String listOrders(OrderListRequest requestData, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("orders");
            }
        }

        if (isLogined) {
            requestData.setToken(getToken());
            OrderListResponse orderResponse = orderService.listOrders(requestData);
            if (orderResponse.isSuccess()) {
                model.addAttribute("orders", orderResponse.getList());
            } else {
                model.addAttribute("orders", new ArrayList<Order>());
            }
            model.addAttribute("requestData", requestData);

            return "order/list";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/order/directly", method = RequestMethod.POST)
    @ResponseBody
    public OrderDetailResponse createOrderDirectly(OrderCreateDirectlyRequest requestData,
            @RequestParam String checkoutUuid) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        OrderDetailResponse response = null;
        if (isLogined) {
            Object uuid = session.getAttribute("checkout_uuid");
            if (null == uuid || !checkoutUuid.equals(uuid.toString())) {
                response = new OrderDetailResponse();
                response.setRet(1500);
            } else {
                requestData.setToken(getToken());
                response = orderService.createOrderDirectly(requestData);
                if (response.isSuccess()) {
                    session.removeAttribute("checkout_uuid");
                }
            }
        } else {
            response = new OrderDetailResponse();
            response.setRet(1001);
        }

        return response;
    }

    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    @ResponseBody
    public OrderDetailResponse createOrder(OrderCreateRequest requestData, @RequestParam String checkoutUuid)
            throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        OrderDetailResponse response = null;
        if (isLogined) {
            Object uuid = session.getAttribute("checkout_uuid");
            if (null == uuid || !checkoutUuid.equals(uuid.toString())) {
                response = new OrderDetailResponse();
                response.setRet(1500);
            } else {
                requestData.setToken(getToken());
                response = orderService.createOrder(requestData);
                if (response.isSuccess()) {
                    session.removeAttribute("checkout_uuid");
                }
            }
        } else {
            response = new OrderDetailResponse();
            response.setRet(1001);
        }

        return response;
    }

    @RequestMapping(value = "/api/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse cancelOrder(OrderCancelRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse baseResponse = null;
        if (isLogined) {
            requestData.setToken(getToken());
            baseResponse = orderService.cancelOrder(requestData);
        } else {
            baseResponse = new BaseResponse();
            baseResponse.setRet(1001);
        }

        return baseResponse;
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public String getOrderDetail(@PathVariable int orderId, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("order/" + orderId);
            }
        }

        if (isLogined) {
            OrderDetailRequest request = new OrderDetailRequest();
            request.setOrderId(orderId);
            request.setToken(getToken());
            model.addAttribute("order", orderService.getOrderDetail(request));

            return "order/detail";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/checkoutDirectly", method = RequestMethod.GET)
    public String checkoutDirectly(int productId, int qty, String guige, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if (isLogined) {
            session.setAttribute("checkout_uuid", System.currentTimeMillis());

            session.setAttribute("qty", qty);

            GuigeModel guigeModel = JacksonUtils.parse(guige, GuigeModel.class);
            session.setAttribute("guige", guigeModel);

            ProductDetailResponse product = productService.getProductDetail(productId);
            session.setAttribute("product", product);
            float price = product.getDiscountPrice() > 0 ? product.getDiscountPrice() : product.getPrice();
            float totalPrice = qty * price;
            session.setAttribute("productTotalPrice", totalPrice);
            if (totalPrice < 299) {
                totalPrice += 15.00;
                session.setAttribute("shipFee", 15.00);
            } else {
                session.setAttribute("shipFee", 0);
            }
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("canUseBaodou", (int) (price * product.getBaodouPercent() * 100));

            AccountResponse profile = getProfile();
            session.setAttribute("totalBhPoints", profile.getBhPoints());
            session.setAttribute("totalQianPoints", profile.getQianPoints());
            session.setAttribute("totalBaodou", profile.getDzPoints());

            AddressDetailResponse address = addressService.getDefaultAddress(getToken());
            if (null != address && address.isSuccess()) {
                session.setAttribute("addressId", address.getAddressId());
                session.setAttribute("address", address);
            }

            return "order/checkout-directly";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/checkoutOrder", method = RequestMethod.GET)
    public String checkoutPage(String products, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if (isLogined) {
            session.setAttribute("checkout_uuid", System.currentTimeMillis());

            CartProductListModel cartProductListModel = JacksonUtils.parse(products, CartProductListModel.class);
            List<CartProductModel> cartProducts = cartProductListModel.getCartProducts();
            session.setAttribute("cartProducts", cartProducts);

            float totalPrice = 0;
            int canUseBaodou = 0;
            StringBuilder cartProductIds = new StringBuilder();
            if (cartProducts != null && cartProducts.size() > 0) {
                for (CartProductModel cartProduct : cartProducts) {
                    float price = cartProduct.getPrice() * cartProduct.getQty();
                    canUseBaodou += (int) (price * cartProduct.getBaodouPercent() * 100);
                    totalPrice += price;
                    cartProductIds.append(cartProduct.getCartProductId()).append(",");
                }
                cartProductIds.delete(cartProductIds.length() - 1, cartProductIds.length());
            }
            session.setAttribute("canUseBaodou", canUseBaodou);

            session.setAttribute("productTotalPrice", totalPrice);
            if (totalPrice < 299) {
                totalPrice += 15.00;
                session.setAttribute("shipFee", 15.00);
            } else {
                session.setAttribute("shipFee", 0);
            }
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("cartProductIds", cartProductIds.toString());

            AccountResponse profile = getProfile();
            session.setAttribute("totalBhPoints", profile.getBhPoints());
            session.setAttribute("totalQianPoints", profile.getQianPoints());
            session.setAttribute("totalBaodou", profile.getDzPoints());

            AddressDetailResponse address = addressService.getDefaultAddress(getToken());
            if (null != address && address.isSuccess()) {
                session.setAttribute("addressId", address.getAddressId());
                session.setAttribute("address", address);
            }

            return "order/checkout";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/order/addresses", method = RequestMethod.GET)
    public String addressListView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("order/addresses");
            }
        }

        if (isLogined) {
            AddressListResponse response = addressService.getAddressList(getToken());
            if (response.isSuccess()) {
                model.addAttribute("addresses", response.getList());
            } else {
                model.addAttribute("addresses", new ArrayList<Address>());
            }

            return "order/addresses";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/order/selectAddress/{addressId}", method = RequestMethod.GET)
    public String selectAddress(@PathVariable int addressId, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if (isLogined) {
            if (null == session.getAttribute("addressId") || !session.getAttribute("addressId").equals(addressId)) {
                AddressDetailRequest request = new AddressDetailRequest();
                request.setAddressId(addressId);
                request.setToken(getToken());
                session.setAttribute("addressId", addressId);
                session.setAttribute("address", addressService.getAddressDetail(request));
            }

            String view = "order/checkout";
            if (null != session.getAttribute("selectAddressRedirectView")) {
                view = (String) session.getAttribute("selectAddressRedirectView");
            }

            return view;
        } else {
            return "redirect:/login";
        }
    }
}
