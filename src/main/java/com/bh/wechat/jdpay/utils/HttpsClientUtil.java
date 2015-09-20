package com.bh.wechat.jdpay.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by wywangzhenlong on 14-8-9.
 */
public class HttpsClientUtil {

    /**
     * 发送json格式请求到指定地址
     * 
     * @param url
     * @param json
     * @return
     */
    public static String sendRequest(String url, String json) {
        int timeout = 5000; // 超时时间
        long responseLength = 0; // 响应长度
        String responseContent = null; // 响应内容
        String strResult = "";
        HttpClient httpClient = new DefaultHttpClient();
        wrapClient(httpClient);
        try {
            HttpParams httpParams = httpClient.getParams();
            httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
            httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            HttpPost httpPost = new HttpPost(url); // 创建HttpPost
            StringEntity se = new StringEntity(json, "UTF-8");
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            se.setContentEncoding("UTF-8");
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost); // 执行POST请求

            // 若状态码为200 ok
            if (response.getStatusLine().getStatusCode() == 200) {
                // 取出回应字串
                strResult = EntityUtils.toString(response.getEntity());
            }
            System.out.println("请求地址: " + httpPost.getURI());
            System.out.println("响应状态: " + response.getStatusLine());
            System.out.println("响应长度: " + responseLength);
            System.out.println("响应内容: " + responseContent);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null)
                httpClient.getConnectionManager().shutdown();// 关闭连接,释放资源
        }
        return strResult;
    }

    /**
     * https 不验证证书
     * 
     * @param httpClient
     */
    public static void wrapClient(HttpClient httpClient) {
        try {
            X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            // TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext ctx = SSLContext.getInstance("TLS");
            // 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[] { xtm }, null);
            // 创建SSLSocketFactory
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
            // 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
