package io.github.taills.common.limiter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 限流器 aop
 *
 * @author taills
 */
@Slf4j
@Aspect
@Component
public class LimiterAop {

    private final String userAgent = "User-Agent";

    private final String xForwardedFor = "x-forwarded-for";

    @Autowired
    private Limiter limiter;

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(io.github.taills.common.limiter.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limit annotation = method.getAnnotation(Limit.class);
        StringBuilder stringBuilderKey = new StringBuilder();
        if (annotation != null) {
            if (annotation.useRemoteHost()) {
                stringBuilderKey.append(request.getRemoteHost());
            }
            if (annotation.useRemoteUser() && StringUtils.isNotBlank(request.getRemoteUser())) {
                stringBuilderKey.append(request.getRemoteUser());
            }
            if (annotation.useUserAgent() && StringUtils.isNotBlank(request.getHeader(userAgent))) {
                stringBuilderKey.append(request.getHeader(userAgent));
            }
            if (annotation.useXForwardedFor() && StringUtils.isNotBlank(request.getHeader(xForwardedFor))) {
                stringBuilderKey.append(request.getHeader(xForwardedFor));
            }
            stringBuilderKey.append(request.getMethod());
            stringBuilderKey.append(request.getRequestURI());
            // key 用以上的这些玩意拼接，取字符串哈希值作为 key
            Integer key = stringBuilderKey.toString().hashCode();
            return limiter.doLimiter(joinPoint, key, annotation.permitsPerSecond(), annotation.timeout(), annotation.timeunit());
        }else{
            return joinPoint.proceed();
        }
    }

}