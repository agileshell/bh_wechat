package com.bh.wechat.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bh.wechat.util.JacksonUtils;

@Component
@Aspect
public class LogAspect {
    private static final Log logger = LogFactory.getLog(LogAspect.class);

    @Pointcut("execution(* com.bh.wechat.service..*.*(..))")
    private void pointCutServices() {}

    @Before("pointCutServices()")
    public void logBefore(JoinPoint joinPoint) {
        List<Object> params = new ArrayList<Object>();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) arg;
                Map<String, Object> fileMap = new HashMap<String, Object>();
                fileMap.put("name", file.getName());
                fileMap.put("origial name", file.getOriginalFilename());
                fileMap.put("size", file.getSize());
                fileMap.put("content type", file.getContentType());
                params.add(fileMap);

                continue;
            }
            params.add(arg);
        }

        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("before calling method: ").append(joinPoint.getSignature().toString())
                .append(" with params: ").append(JacksonUtils.stringify(params));

        logger.info(strBuffer.toString());
    }

    @AfterReturning(pointcut = "pointCutServices()", returning = "result")
    public void logReturning(JoinPoint joinPoint, Object result) {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("after calling method: ").append(joinPoint.getSignature().toString()).append(" with result: ")
                .append(result);

        logger.info(strBuffer.toString());
    }
}
