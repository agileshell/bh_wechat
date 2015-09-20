package com.bh.wechat.gateway;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月4日 下午7:30:18
 */
public class GatewayProxyRequest<T> {

    private static final String WH = "?";

    private static final String AND = "&";

    private static final String EQ = "=";

    private Class<T> responseType;

    private String uri;

    private HttpMethod method;

    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

    private final Map<String, Object> uriVariables = new HashMap<String, Object>();

    private final MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();

    public Class<T> getResponseType() {
        return responseType;
    }

    public GatewayProxyRequest<T> responseType(Class<T> responseType) {
        this.responseType = responseType;
        return this;
    }

    public String getUri() {
        if (HttpMethod.POST == method || parameters.isEmpty()) {
            return uri;
        }
        StringBuilder sb = new StringBuilder();
        for (Entry<String, List<Object>> e : parameters.entrySet()) {
            List<Object> params = e.getValue();
            if (CollectionUtils.isEmpty(params)) {
                continue;
            }
            for (Object param : params) {
                if (param == null) {
                    param = StringUtils.EMPTY;
                }
                sb.append(e.getKey()).append(EQ).append(param.toString()).append(AND);
            }
        }
        String queryString = sb.toString();
        return uri + WH + StringUtils.substring(queryString, 0, queryString.length() - 1);
    }

    public HttpEntity<?> getRequestEntity() {
        if (HttpMethod.POST == method) {
            return new HttpEntity<MultiValueMap<String, Object>>(getParameters(), getRequestHeader());
        }
        return new HttpEntity<Object>(getRequestHeader());
    }

    public HttpHeaders getRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAll(getHeaders().toSingleValueMap());
        return headers;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public GatewayProxyRequest<T> uri(String uri) {
        this.uri = uri;
        return this;
    }

    public GatewayProxyRequest<T> method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public GatewayProxyRequest<T> apiUri(ApiUri uri) {
        this.uri = uri.uri;
        this.method = uri.method;
        return this;
    }

    public Map<String, Object> getUriVariables() {
        return uriVariables;
    }

    public GatewayProxyRequest<T> uriVariable(String name, Object value) {
        if (value == null) {
            return this;
        }

        getUriVariables().put(name, value.toString());
        return this;
    }

    public GatewayProxyRequest<T> uriVariables(Map<String, Object> uriVariables) {
        if (uriVariables == null || uriVariables.isEmpty()) {
            return this;
        }
        for (Map.Entry<String, Object> e : uriVariables.entrySet()) {
            uriVariable(e.getKey(), e.getValue().toString());
        }
        return this;
    }

    public MultiValueMap<String, Object> getParameters() {
        return parameters;
    }

    public GatewayProxyRequest<T> parameter(String name, Object value) {
        if (null == value) {
            return this;
        }

        getParameters().add(name, value.toString());
        return this;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }

    public GatewayProxyRequest<T> header(String name, String value) {
        if (value == null) {
            return this;
        }

        getHeaders().add(name, value.toString());
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
