package io.github.taills.common.limiter.guavalimiter;

import io.github.taills.common.limiter.Limiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @ClassName GuavaLimiterConfiguration
 * @Description 在 Beans 中找不到限流器时，new 一个 GuavaLimiter
 * @Author taills
 * @Date 2022/1/13 7:13 PM
 **/
@Configuration
public class GuavaLimiterConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public Limiter limiter() {
        Map<String, Limiter> beans = applicationContext.getBeansOfType(Limiter.class);
        // 判断是否已经有 Limiter 的实例，没有再创建 GuavaLimiter
        if (beans.size() == 0) {
            return new GuavaLimiter();
        } else {
            return (Limiter) beans.values().toArray()[0];
        }
    }
}
