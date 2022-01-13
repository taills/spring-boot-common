package io.github.taills.common.limiter;

import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Limiter
 * @Description 限流器接口
 * @Author taills
 * @Date 2022/1/13 7:00 PM
 **/
public interface Limiter {
    /**
     * 限流器实现AOP
     *
     * @param proceedingJoinPoint
     * @param key
     * @return
     * @throws Throwable
     */
    Object doLimiter(ProceedingJoinPoint proceedingJoinPoint, Integer key, double permitsPerSecond, long timeout, TimeUnit timeUnit) throws Throwable;
}
