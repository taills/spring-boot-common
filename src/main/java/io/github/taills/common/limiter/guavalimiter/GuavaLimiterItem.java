package io.github.taills.common.limiter.guavalimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName GuavaLimiter
 * @Description
 * @Author taills
 * @Date 2022/1/13 4:32 PM
 **/
@Data
@AllArgsConstructor
public class GuavaLimiterItem {

    /**
     * 上次使用时间
     */
    private Date lastActive;
    /**
     * RateLimiter
     */
    private RateLimiter rateLimiter;
}
