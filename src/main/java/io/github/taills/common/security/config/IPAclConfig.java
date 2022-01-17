package io.github.taills.common.security.config;

import io.github.taills.common.security.interceptor.IPAclInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName IPAddressAclConfig
 * @Description
 * @Author nil
 * @Date 2022/1/18 12:30 AM
 **/
@Configuration
public class IPAclConfig extends WebMvcConfigurationSupport {

    @Autowired
    private IPAclInterceptor ipAclInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipAclInterceptor).addPathPatterns("/**").excludePathPatterns("/public/**");
        super.addInterceptors(registry);
    }
}
