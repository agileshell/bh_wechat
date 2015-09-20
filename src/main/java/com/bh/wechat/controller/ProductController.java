package com.bh.wechat.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.ProductListRequest;
import com.bh.wechat.response.CategoryListResponse;
import com.bh.wechat.response.Product;
import com.bh.wechat.response.ProductListResponse;

@Controller
public class ProductController extends BaseController {

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    @ResponseBody
    public ProductListResponse listProducts(ProductListRequest requestData) throws BhException {
        return productService.listProducts(requestData);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String listProducts(ProductListRequest requestData, Model model) throws BhException {
        ProductListResponse productsResponse = productService.listProducts(requestData);
        if (productsResponse.isSuccess()) {
            model.addAttribute("products", null != productsResponse.getList() ? productsResponse.getList()
                    : new ArrayList<Product>());
        } else {
            model.addAttribute("products", new ArrayList<Product>());
        }
        model.addAttribute("requestData", requestData);

        return "product/list";
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String getProductDetail(@PathVariable int productId, Model model) throws BhException {
        model.addAttribute("product", productService.getProductDetail(productId));

        return "product/detail";
    }

    @RequestMapping(value = "/product/description/{productId}", method = RequestMethod.GET)
    public String getProductDdescription(@PathVariable int productId, Model model) throws BhException {
        model.addAttribute("product", productService.getProductDetail(productId));

        return "product/description";
    }

    @RequestMapping(value = "/api/categories", method = RequestMethod.GET)
    @ResponseBody
    public CategoryListResponse listCategories() throws BhException {
        return productService.getCategoryList();
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String listCategories(Model model) throws BhException {
        return "product/category";
    }
}
