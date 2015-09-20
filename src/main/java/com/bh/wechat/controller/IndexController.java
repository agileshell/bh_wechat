package com.bh.wechat.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bh.wechat.constant.GlobalProperties;
import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.ProductListRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.Campaign;
import com.bh.wechat.response.CampaignListResponse;
import com.bh.wechat.response.Help;
import com.bh.wechat.response.HelpListResponse;
import com.bh.wechat.response.Product;
import com.bh.wechat.response.ProductListResponse;
import com.bh.wechat.wx.model.JsSignature;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) throws BhException {
        setHomePageData(model);

        return "home";
    }

    private void setHomePageData(Model model) throws BhException {
        CampaignListResponse campaignResponse = commonService.listCampaigns();
        if (campaignResponse.isSuccess()) {
            model.addAttribute("campaigns", null != campaignResponse.getList() ? campaignResponse.getList()
                    : new ArrayList<Campaign>());
        } else {
            model.addAttribute("campaigns", new ArrayList<Campaign>());
        }

        ProductListRequest productListRequest = new ProductListRequest();
        productListRequest.setTag("featured");
        ProductListResponse productsResponse = productService.listProducts(productListRequest);
        if (productsResponse.isSuccess()) {
            model.addAttribute("products", null != productsResponse.getList() ? productsResponse.getList()
                    : new ArrayList<Product>());
        } else {
            model.addAttribute("products", new ArrayList<Product>());
        }

        if (isLogin()) {
            AccountResponse profile = getProfile();
            int userId = profile.getUserId();
            model.addAttribute("recommendUrl", GlobalProperties.WX_DOAMIN.concat("recommend/" + userId));
        } else {
            model.addAttribute("recommendUrl", GlobalProperties.WX_DOAMIN.concat("recommend/0"));
        }
        model.addAttribute("doamin", GlobalProperties.WX_DOAMIN);

        JsSignature jsSignature = getJsSignature(GlobalProperties.WX_DOAMIN + "home");
        model.addAttribute("jsSignature", jsSignature);
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public String aboutus() {
        return "about-us";
    }

    @RequestMapping(value = "/helps", method = RequestMethod.GET)
    public String listHelps(Model model) throws BhException {
        HelpListResponse helpListResponse = commonService.listHelps();
        if (helpListResponse.isSuccess()) {
            model.addAttribute("helps", null != helpListResponse.getList() ? helpListResponse.getList()
                    : new ArrayList<Help>());
        } else {
            model.addAttribute("helps", new ArrayList<Help>());
        }

        return "help/list";
    }

    @RequestMapping(value = "/help/{helpId}", method = RequestMethod.GET)
    public String getHelpDetail(@PathVariable int helpId, Model model) throws BhException {
        model.addAttribute("help", commonService.getHelpDetail(helpId));

        return "help/detail";
    }

    @RequestMapping(value = "/campaigns", method = RequestMethod.GET)
    public String listCampaigns(Model model) throws BhException {
        CampaignListResponse campaignResponse = commonService.listCampaigns();
        if (campaignResponse.isSuccess()) {
            model.addAttribute("campaigns", campaignResponse.getList());
        } else {
            model.addAttribute("campaigns", new ArrayList<Campaign>());
        }

        return "campaign/list";
    }

    @RequestMapping(value = "/campaign/{campaignId}", method = RequestMethod.GET)
    public String getCampaignDetail(@PathVariable int campaignId, Model model) throws BhException {
        model.addAttribute("campaign", commonService.getCampaignDetail(campaignId));

        return "campaign/detail";
    }
}
