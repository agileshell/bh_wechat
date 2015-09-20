package test;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.GatewayProxyRequest;
import com.bh.wechat.request.AddressCreateRequest;
import com.bh.wechat.request.AddressDetailRequest;
import com.bh.wechat.request.AddressUpdateRequest;
import com.bh.wechat.request.CartProductCreateRequest;
import com.bh.wechat.request.CartProductDeleteRequest;
import com.bh.wechat.request.CartProductListRequest;
import com.bh.wechat.request.LocationRequest;
import com.bh.wechat.request.OrderCancelRequest;
import com.bh.wechat.request.OrderDetailRequest;
import com.bh.wechat.request.OrderListRequest;
import com.bh.wechat.request.ProductListRequest;
import com.bh.wechat.request.RegistryRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.AddressDetailResponse;
import com.bh.wechat.response.AddressListResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.CampaignDetailResponse;
import com.bh.wechat.response.CampaignListResponse;
import com.bh.wechat.response.CartProductListResponse;
import com.bh.wechat.response.CategoryListResponse;
import com.bh.wechat.response.HelpDetailResponse;
import com.bh.wechat.response.HelpListResponse;
import com.bh.wechat.response.LocationResponse;
import com.bh.wechat.response.MessageDetailResponse;
import com.bh.wechat.response.MessageListResponse;
import com.bh.wechat.response.OrderDetailResponse;
import com.bh.wechat.response.OrderListResponse;
import com.bh.wechat.response.ProductDetailResponse;
import com.bh.wechat.response.ProductListResponse;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月17日 上午11:37:26
 */
public class HttpGatewayApi extends BaseHttpGatewayApi {

    @Test
    public String testRedis() throws BhException {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setUsePool(true);
        connectionFactory.setDatabase(1);
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.setTimeout(4000);
        connectionFactory.afterPropertiesSet();
        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
        redisTemplate.afterPropertiesSet();

        redisTemplate.opsForValue().set("name", "fei.liu", 1000, TimeUnit.MINUTES);

        return redisTemplate.opsForValue().get("name");
    }

    @Test
    public String test() throws BhException {
        GatewayProxyRequest<String> request = new GatewayProxyRequest<String>().responseType(String.class)
                .uri("http://127.0.0.1:8080/test.html").method(HttpMethod.POST).parameter("name", "fei.liu刘飞");
        return httpGatewayTemplate.invoke(request);
    }

    @Test
    @AssertResponse("'获取地区 - ' + $success + ' 响应报文 : ' + $returnObj")
    public LocationResponse getLocations() throws BhException {
        LocationRequest request = new LocationRequest();
        request.setParentId(1);
        return httpGatewayTemplate.invoke(LocationResponse.class, request);
    }

    @Test
    @AssertResponse("'关注公众号 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse followWechat() throws BhException {
        GatewayProxyRequest<BaseResponse> request = new GatewayProxyRequest<BaseResponse>()
                .responseType(BaseResponse.class).apiUri(ApiUri.FOLLOW_WECHAT)
                .parameter("openid", "c9b14384abb5fa1ba08a129e").parameter("brokerId", 0);
        return httpGatewayTemplate.invoke(request);
    }

    @Test
    @AssertResponse("'注册 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AccountResponse register() throws BhException {
        // {"ret":0,"msg":"","token":"2015082623425340809047V2FPB4P8","userId":40903,"userName":"13611659742fei.liu","bhPoints":0,"dzPoints":0,"qianPoints":0}
        RegistryRequest request = new RegistryRequest();
        request.setMobile("13611659742");
        request.setOpenid("c9b14384abb5fa1ba08a129e");
        request.setPassword("bh123456");
        request.setUserName("13611659742fei.liu");
        return httpGatewayTemplate.invoke(AccountResponse.class, request);
    }

    @Test
    @AssertResponse("'登录 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AccountResponse login() throws BhException {
        GatewayProxyRequest<AccountResponse> req = new GatewayProxyRequest<AccountResponse>()
                .responseType(AccountResponse.class).apiUri(ApiUri.LOGIN).parameter("account", "13611659742fei.liu")
                .parameter("password", "bh123456");
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'获取用户基本信息 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AccountResponse getUserProfile() throws BhException {
        GatewayProxyRequest<AccountResponse> req = new GatewayProxyRequest<AccountResponse>()
                .responseType(AccountResponse.class).apiUri(ApiUri.GET_USER_INFO)
                .parameter("token", "2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'修改登录密码 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse changePassword() throws BhException {
        GatewayProxyRequest<BaseResponse> req = new GatewayProxyRequest<BaseResponse>()
                .responseType(BaseResponse.class).apiUri(ApiUri.CHANGE_PASSWORD)
                .parameter("token", "2015082623425340809047V2FPB4P8").parameter("password", "bh123456")
                .parameter("oldPassword", "bh12345612");
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'修改支付密码 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse changePayPassword() throws BhException {
        GatewayProxyRequest<BaseResponse> req = new GatewayProxyRequest<BaseResponse>()
                .responseType(BaseResponse.class).apiUri(ApiUri.CHANGE_PAYPASSWD).parameter("token", "")
                .parameter("password", "bh123456").parameter("oldPassword", "bh12345612");
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'分类 - ' + $success + ' 响应报文 : ' + $returnObj")
    public CategoryListResponse getCategoryList() throws BhException {
        GatewayProxyRequest<CategoryListResponse> req = new GatewayProxyRequest<CategoryListResponse>().responseType(
                CategoryListResponse.class).apiUri(ApiUri.GET_CATEGORIES);
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @Ignore("活动列表")
    @AssertResponse("'分类 - ' + $success + ' 响应报文 : ' + $returnObj")
    public CampaignListResponse listCampaigns() throws BhException {
        GatewayProxyRequest<CampaignListResponse> req = new GatewayProxyRequest<CampaignListResponse>().responseType(
                CampaignListResponse.class).apiUri(ApiUri.GET_CAMPAIGNS);
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @Ignore("对方木有提供")
    @AssertResponse("'活动详情 - ' + $success + ' 响应报文 : ' + $returnObj")
    public CampaignDetailResponse getCampaignDetail() throws BhException {
        GatewayProxyRequest<CampaignDetailResponse> req = new GatewayProxyRequest<CampaignDetailResponse>()
                .responseType(CampaignDetailResponse.class).apiUri(ApiUri.GET_CAMPAIGN_DETAIL)
                .uriVariable("campaignId", 1);
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @Ignore("对方木有提供")
    @AssertResponse("'帮助列表 - ' + $success + ' 响应报文 : ' + $returnObj")
    public HelpListResponse listHelps() throws BhException {
        GatewayProxyRequest<HelpListResponse> req = new GatewayProxyRequest<HelpListResponse>().responseType(
                HelpListResponse.class).apiUri(ApiUri.GET_HELPS);
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @Ignore("对方木有提供")
    @AssertResponse("'帮助详情 - ' + $success + ' 响应报文 : ' + $returnObj")
    public HelpDetailResponse getHelpDetail() throws BhException {
        GatewayProxyRequest<HelpDetailResponse> req = new GatewayProxyRequest<HelpDetailResponse>()
                .responseType(HelpDetailResponse.class).apiUri(ApiUri.GET_HELP_DETAIL).uriVariable("helpId", 1);
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'获取地址列表 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AddressListResponse getAddressList() throws BhException {
        GatewayProxyRequest<AddressListResponse> request = new GatewayProxyRequest<AddressListResponse>()
                .parameter("token", "2015082623425340809047V2FPB4P8").responseType(AddressListResponse.class)
                .apiUri(ApiUri.GET_ADDRESSES);
        return httpGatewayTemplate.invoke(request);
    }

    @Test
    @AssertResponse("'新增地址 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AddressDetailResponse createAddress() throws BhException {
        AddressCreateRequest request = new AddressCreateRequest();
        request.setAddress("assssssss");
        request.setIsDefault(false);
        request.setLocationId(0);
        request.setMobile("13701659642");
        request.setName("大飞哥儿");
        request.setToken("2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(AddressDetailResponse.class, request);
    }

    @Test
    @AssertResponse("'获取地址详情 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AddressDetailResponse getAddressDetail() throws BhException {
        AddressDetailRequest request = new AddressDetailRequest();
        request.setAddressId(1);
        request.setToken("2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(AddressDetailResponse.class, request);
    }

    @Test
    @AssertResponse("'修改地址 - ' + $success + ' 响应报文 : ' + $returnObj")
    public AddressDetailResponse updateAddress() throws BhException {
        AddressUpdateRequest request = new AddressUpdateRequest();
        request.setAddress("adsd");
        request.setIsDefault(false);
        request.setLocationId(0);
        request.setMobile("13701659642");
        request.setName("大飞哥儿");
        request.setToken("2015082623425340809047V2FPB4P8");
        request.setAddressId(1);
        return httpGatewayTemplate.invoke(AddressDetailResponse.class, request);
    }

    @Test
    @AssertResponse("'删除地址 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse deleteAddress() throws BhException {
        GatewayProxyRequest<BaseResponse> request = new GatewayProxyRequest<BaseResponse>()
                .parameter("token", "2015082623425340809047V2FPB4P8").uriVariable("addressId", 1)
                .responseType(BaseResponse.class).apiUri(ApiUri.DELETE_ADDRESS);
        return httpGatewayTemplate.invoke(request);
    }

    @Test
    @AssertResponse("'设置默认地址 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse setDefaultAddress() throws BhException {
        GatewayProxyRequest<BaseResponse> request = new GatewayProxyRequest<BaseResponse>()
                .parameter("token", "2015082623425340809047V2FPB4P8").uriVariable("addressId", 1)
                .responseType(BaseResponse.class).apiUri(ApiUri.SET_DEFAULT_ADDRESS);
        return httpGatewayTemplate.invoke(request);
    }

    @Test
    @AssertResponse("'获取购物车商品列表 - ' + $success + ' 响应报文 : ' + $returnObj")
    public CartProductListResponse getCartProducts() throws BhException {
        CartProductListRequest request = new CartProductListRequest();
        request.setToken("2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(CartProductListResponse.class, request);
    }

    @Test
    @AssertResponse("'把商品加入购物车 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse addCartProduct() throws BhException {
        CartProductCreateRequest request = new CartProductCreateRequest();
        request.setToken("2015082623425340809047V2FPB4P8");
        request.setGuige("{\"color\":[\"做v\"],\"size\":[\"水滴\"]}");
        request.setProductId(2984);
        request.setQty(1);
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

    @Test
    @AssertResponse("'从购物车中移除商品 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse removeCartProduct() throws BhException {
        CartProductDeleteRequest request = new CartProductDeleteRequest();
        request.setCartProductId(2984);
        request.setToken("2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

    @Test
    @AssertResponse("'站内信息列表 - ' + $success + ' 响应报文 : ' + $returnObj")
    public MessageListResponse listMessages() throws BhException {
        GatewayProxyRequest<MessageListResponse> req = new GatewayProxyRequest<MessageListResponse>()
                .responseType(MessageListResponse.class).apiUri(ApiUri.GET_MESSAGES)
                .parameter("token", "2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'阅读站内消息 - ' + $success + ' 响应报文 : ' + $returnObj")
    public MessageDetailResponse getMessageDetail() throws BhException {
        GatewayProxyRequest<MessageDetailResponse> req = new GatewayProxyRequest<MessageDetailResponse>()
                .responseType(MessageDetailResponse.class).apiUri(ApiUri.GET_MESSAGE_DETAIL)
                .parameter("token", "2015082623425340809047V2FPB4P8").uriVariable("messageId", 44);
        return httpGatewayTemplate.invoke(req);
    }

    @Test
    @AssertResponse("'获取订单列表 - ' + $success + ' 响应报文 : ' + $returnObj")
    public OrderListResponse getOrders() throws BhException {
        OrderListRequest request = new OrderListRequest();
        request.setToken("2015082623425340809047V2FPB4P8");
        request.setLimit(20);
        request.setOffset(0);
        request.setStatus("");
        return httpGatewayTemplate.invoke(OrderListResponse.class, request);
    }

    @Test
    @AssertResponse("'获取订单详情 - ' + $success + ' 响应报文 : ' + $returnObj")
    public OrderDetailResponse getOrderDetail() throws BhException {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setOrderId(1);
        request.setToken("2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(OrderDetailResponse.class, request);
    }

    @Test
    @AssertResponse("'取消订单 - ' + $success + ' 响应报文 : ' + $returnObj")
    public BaseResponse cancelOrder() throws BhException {
        OrderCancelRequest request = new OrderCancelRequest();
        request.setOrderId(1);
        request.setToken("2015082623425340809047V2FPB4P8");
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

    @Test
    @AssertResponse("'商品列表 - ' + $success + ' 响应报文 : ' + $returnObj")
    public ProductListResponse listProducts() throws BhException {
        ProductListRequest request = new ProductListRequest();
        return httpGatewayTemplate.invoke(ProductListResponse.class, request);
    }

    @Test
    @AssertResponse("'商品详情 - ' + $success + ' 响应报文 : ' + $returnObj")
    public ProductDetailResponse getProductDetail() throws BhException {
        GatewayProxyRequest<ProductDetailResponse> req = new GatewayProxyRequest<ProductDetailResponse>()
                .responseType(ProductDetailResponse.class).apiUri(ApiUri.GET_PRODUCT_DETAIL)
                .uriVariable("productId", 2984);
        return httpGatewayTemplate.invoke(req);
    }
}