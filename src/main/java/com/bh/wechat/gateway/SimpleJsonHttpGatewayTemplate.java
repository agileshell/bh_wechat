package com.bh.wechat.gateway;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import com.bh.wechat.constant.ResponseCode;
import com.bh.wechat.exception.BhException;
import com.bh.wechat.exception.BhExceptionFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 通过JSON格式实现简单的HTTP网关服务代理.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月4日 下午3:29:17
 */
public class SimpleJsonHttpGatewayTemplate implements HttpGatewayTemplate, InitializingBean {

    private static final Log log = LogFactory.getLog("json.http.gateway");

    private static final String CLASS_NAME = "class";

    private RestTemplate restTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(jackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                log.error("Invoking " + request.getURI() + " - " + request.getMethod() + " -H " + request.getHeaders()
                        + " -B " + new String(body));
                return execution.execute(request, body);
            }
        });
    }

    @Override
    public <T> T invoke(final GatewayProxyRequest<T> request) throws BhException {
        long startTime = System.nanoTime();
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(request.getUri(), request.getMethod(),
                    request.getRequestEntity(), request.getResponseType(), request.getUriVariables());
            return responseEntity.getBody();
        } catch (Throwable e) {
            throw BhExceptionFactory.getException(BhException.class, ResponseCode.SERVER_ERROR, e,
                    e.getLocalizedMessage());
        } finally {
            log.error("Invoked Gateway URI[" + request.getUri() + "] execute time : "
                    + ((System.nanoTime() - startTime) / NANO_UNIT) + "s");
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType, Object request) throws BhException {
        return invoke(extractGatewayProxyRequest(responseType, request));
    }

    private <T> GatewayProxyRequest<T> extractGatewayProxyRequest(Class<T> responseType, Object request)
            throws BhException {
        Class<?> requestType = request.getClass();
        URI uri = AnnotationUtils.findAnnotation(requestType, URI.class);
        if (uri == null) {
            throw BhExceptionFactory.getException(BhException.class, ResponseCode.SERVER_ERROR, null,
                    "Can't Proxy Request : " + request);
        }
        GatewayProxyRequest<T> gatewayRequest = new GatewayProxyRequest<T>().responseType(responseType).apiUri(
                uri.uri());
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(requestType);
        for (PropertyDescriptor property : pds) {
            String name = property.getName();
            if (StringUtils.equals(name, CLASS_NAME)) {
                continue;
            }
            Object val = ReflectionUtils.invokeMethod(property.getReadMethod(), request);
            Field parameterField = ReflectionUtils.findField(requestType, name);
            PathVariable uriVariable = AnnotationUtils.getAnnotation(parameterField, PathVariable.class);
            if (uriVariable != null) {
                String uriVariableName = uriVariable.value();
                if (StringUtils.isBlank(uriVariableName)) {
                    uriVariableName = name;
                }
                gatewayRequest.uriVariable(uriVariableName, val);
            }
            Parameter parameter = AnnotationUtils.getAnnotation(parameterField, Parameter.class);
            if (parameter != null) {
                String parameterName = parameter.name();
                if (StringUtils.isBlank(parameterName)) {
                    parameterName = name;
                }
                if (parameter.required()) {
                    gatewayRequest.parameter(parameterName, val);
                } else {
                    if (val != null) {
                        gatewayRequest.parameter(parameterName, val);
                    }
                }
            }
            if (val == null) {
                continue;
            }
            Header header = AnnotationUtils.getAnnotation(parameterField, Header.class);
            if (header != null) {
                String headerName = header.value();
                if (StringUtils.isBlank(headerName)) {
                    headerName = name;
                }
                gatewayRequest.header(headerName, String.valueOf(val));
            }
        }
        return gatewayRequest;
    }
}
