package io.github.taills.common.security.config;

import io.github.taills.common.security.interceptor.IPAclInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @ClassName CommonMvcConfig
 * @Description
 * @Author nil
 * @Date 2022/1/18 12:30 AM
 **/
@Configuration
public class CommonMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private IPAclInterceptor ipAclInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipAclInterceptor).addPathPatterns("/**").excludePathPatterns("/public/**");
        super.addInterceptors(registry);
    }

    /**
     * 这里重新配置 swagger 的资源，要不然页面 404
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        super.addResourceHandlers(registry);
    }
}
