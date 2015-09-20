package com.bh.wechat.exception;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.bh.wechat.constant.ResponseCode;

public class BhExceptionFactory {

    public static final Logger LOGGER = Logger.getLogger(BhExceptionFactory.class);

    private BhExceptionFactory() {}

    public static <T extends BhException> T getException(Class<T> exceptionClass, ResponseCode code, Throwable e, String... messages) {
        T t = null;
        if (null != messages) {
            List<String> messageList = Arrays.asList(messages);
            t = getException(exceptionClass, code, messageList, e);

        }
        return t;
    }

    public static <T extends BhException> T getException(Class<T> exceptionClass, ResponseCode code,
            List<String> messages, Throwable ex) {
        T t = null;
        try {
            t = exceptionClass.newInstance();
            for (String message : messages) {
                t.addErrorMessage(message);
            }
            t.setCode(code);
            if (ex != null) t.initCause(ex);
        } catch (InstantiationException e) {
            LOGGER.debug(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LOGGER.debug(e.getMessage(), e);
        }
        return t;
    }
}
