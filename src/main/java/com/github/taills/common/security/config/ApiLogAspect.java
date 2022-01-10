package com.github.taills.common.security.config;

import com.github.taills.common.util.SnowFlake;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @ClassName ApiLogAspect
 * @Description
 * @Author nil
 * @Date 2021/10/22 10:24 下午
 **/
@Aspect
@Component
@Slf4j
public class ApiLogAspect {

    private final String RequestIdHeaderName = "RequestId";

    @Pointcut("@within(com.github.taills.common.annotation.ApiResponseBody) || this(com.github.taills.common.controller.BaseController)")
    public void apiLog() {
    }

    private final static Gson GSON = new Gson();

    @Before("apiLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (null == request.getAttribute(RequestIdHeaderName)) {
            request.setAttribute(RequestIdHeaderName, SnowFlake.get().nextSid());
        }
        log.info("========================================== {} ==========================================", request.getAttribute(RequestIdHeaderName));
        log.info("{}\tURL\t\t\t\t\t{}", request.getAttribute(RequestIdHeaderName), request.getRequestURL().toString());
        log.info("{}\tHTTP Method\t\t\t{}", request.getAttribute(RequestIdHeaderName), request.getMethod());
        log.info("{}\tClass Method\t\t{}.{}", request.getAttribute(RequestIdHeaderName), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("{}\tIP\t\t\t\t\t{}", request.getAttribute(RequestIdHeaderName), request.getRemoteAddr());
        if (request.getUserPrincipal() != null) {
            log.info("{}\tUser\t\t\t\t{}", request.getAttribute(RequestIdHeaderName), request.getUserPrincipal().getName());
        }
        log.info("{}\tRequest Args\t\t{}", request.getAttribute(RequestIdHeaderName), GSON.toJson(joinPoint.getArgs()));
    }

    @After("apiLog()")
    public void doAfter() {
    }

    @Around("apiLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        log.info("{}\tResponse Args\t\t{}", request.getAttribute(RequestIdHeaderName), new Gson().toJson(result));
        log.info("{}\tTime-Consuming\t\t{} ms", request.getAttribute(RequestIdHeaderName), System.currentTimeMillis() - startTime);
        return result;
    }

}
