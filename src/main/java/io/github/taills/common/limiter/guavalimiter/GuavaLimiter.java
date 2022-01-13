package io.github.taills.common.limiter.guavalimiter;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import io.github.taills.common.exception.ExceptionManager;
import io.github.taills.common.limiter.Limit;
import io.github.taills.common.limiter.Limiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * guava 限流器，适用于单实例内部
 *
 * @author taills
 */
@Slf4j
public class GuavaLimiter implements Limiter {

    private final Map<Integer, GuavaLimiterItem> limiterMap = Maps.newConcurrentMap();


    public GuavaLimiter() {
        log.debug("GuavaLimiter 被实例化");
    }

    @Override
    public Object doLimiter(ProceedingJoinPoint proceedingJoinPoint, Integer key, double permitsPerSecond, long timeout, TimeUnit timeUnit) throws Throwable {
        GuavaLimiterItem guavaLimiterItem;
        if (!limiterMap.containsKey(key)) {
            RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);
            guavaLimiterItem = new GuavaLimiterItem(new Date(), rateLimiter);
            limiterMap.put(key, guavaLimiterItem);
        } else {
            guavaLimiterItem = limiterMap.get(key);
            //刷新令牌使用时间
            synchronized (this) {
                limiterMap.get(key).setLastActive(new Date());
            }
        }
        boolean acquired = guavaLimiterItem.getRateLimiter().tryAcquire(timeout, timeUnit);
        if (!acquired) {
            throw ExceptionManager.create(9999);
        }
        return proceedingJoinPoint.proceed();
    }

    /**
     * 清理限流器，需要入口类增加 @EnableScheduling 注解，开启定时任务，并配置线程池大小，默认为单线程
     * 清理时间间隔为 60 秒
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void scheduledCleanupTask() {
        synchronized (this) {
            Date expire = new Date(System.currentTimeMillis() - 60 * 1000);
            Map<Integer, GuavaLimiterItem> collect = limiterMap.entrySet().stream()
                    .filter(x -> x.getValue().getLastActive().after(expire))
                    .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
            limiterMap.clear();
            limiterMap.putAll(collect);
        }
    }
}