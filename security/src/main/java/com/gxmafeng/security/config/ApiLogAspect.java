package com.gxmafeng.security.config;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    @Pointcut("@within(org.springframework.web.bind.annotation.ResponseBody)")
    public void apiLog() {
    }

    private final static Gson gson = new Gson();

    @Before("apiLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("========================================== Start ==========================================");
        log.info("URL            : {}", request.getRequestURL().toString());
        log.info("HTTP Method    : {}", request.getMethod());
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP             : {}", request.getRemoteAddr());
        if (request.getUserPrincipal() != null)
            log.info("User           : {}", request.getUserPrincipal().getName());
        log.info("Request Args   : {}", gson.toJson(joinPoint.getArgs()));
    }

    @After("apiLog()")
    public void doAfter() {
        log.info("=========================================== End ===========================================");

        log.info("");
    }

    @Around("apiLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        log.info("Response Args  : {}", new Gson().toJson(result));
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

}
