package io.github.taills.common.limiter;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流器注解，在 Controller 中使用
 * 把参数 useUserAgent、useXForwardedFor、useRemoteHost、useRemoteUser 都设置为 false，则仅针对 Method + RequestURI 来统一限流，适合秒杀类接口。
 * 或者单独开启 useRemoteUser ，且用户登录的情况下，则为针对 用户名 + Method + RequestURI 进行限流
 * @author taills
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {
    /**
     * 每秒产生的令牌数，支持小数。如每秒 0.1，一分钟产生 6 个
     *
     * @return
     */
    double permitsPerSecond() default 1;

    /**
     * 获取令牌超时时间，超过这个时间获取不到令牌则放弃获取，抛出异常。默认值为 5
     *
     * @return
     */
    long timeout() default 5;

    /**
     * 超时时间的单位，默认秒
     *
     * @return
     */
    TimeUnit timeunit() default TimeUnit.SECONDS;

    /**
     * 是否使用 User Agent 来参与生成 key。
     * !!! 有被伪造的风险
     * @return
     */
    boolean useUserAgent() default false;

    /**
     * 是否使用 X-Forwarded-For 来参与生成 key。
     * !!! 有被伪造的风险。
     * 若spring boot 前置有 nginx 反向代理的话，可在请求入口处做以下配置：
     *
     * proxy_set_header X-Forwarded-For $remote_addr;
     *
     * 可过滤掉伪造的 X-Forwarded-For ，相当于在第一层 nginx 转发时，把 remote host 放入 X-Forwarded-For 进行转发。
     * @return
     */
    boolean useXForwardedFor() default false;

    /**
     * 是否使用 Remote Host 来参与生成 key。
     * 这里如果前置有 nginx 的话，拿到的都是 nginx 的 ip。
     * @return
     */
    boolean useRemoteHost() default false;

    /**
     * 是否使用 Remote User 来参与生成 key。
     * 用户未登录的情况下，此参数为 null，用户登录的情况下，此参数为 用户名。
     * @return
     */
    boolean useRemoteUser() default false;
}