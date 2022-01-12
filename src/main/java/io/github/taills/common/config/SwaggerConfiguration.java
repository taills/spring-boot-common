package io.github.taills.common.config;


import io.github.taills.common.annotation.ApiResponseBody;
import io.github.taills.common.filter.SwaggerJavascriptHookFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName SwaggerConfiguration
 * @Description
 * @Author nil
 * @Date 2021/10/21 9:51 下午
 **/
@Configuration
@Slf4j
public class SwaggerConfiguration {
    private final SwaggerProperties swaggerProperties;

    @Value("classpath:/META-INF/resources/webjars/springfox-swagger-ui/springfox.js")
    private Resource springfoxJs;

    @Value("classpath:/springfox-append.js")
    private Resource springfoxAppendJs;


    @Autowired
    private SwaggerJavascriptHookFilter swaggerJavascriptHookFilter;

    public SwaggerConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
        log.info("swagger 配置 {}", this.swaggerProperties);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .enable(this.swaggerProperties.getEnable())
                .apiInfo(new ApiInfoBuilder()
                        .title(this.swaggerProperties.getApplicationName())
                        .description(this.swaggerProperties.getApplicationDescription())
                        .contact(new Contact(this.swaggerProperties.getContact().getName(),
                                this.swaggerProperties.getContact().getUrl(),
                                this.swaggerProperties.getContact().getEmail()))
                        .version(this.swaggerProperties.getApplicationVersion())
                        .build())
                .ignoredParameterTypes(HttpServletRequest.class)
                .ignoredParameterTypes(HttpSession.class)
                .groupName(this.swaggerProperties.getGroupName())
                .select()
                //apis： 添加swagger接口提取范围
//                .apis(RequestHandlerSelectors.basePackage("io.github.taills"))
                .apis(RequestHandlerSelectors.withClassAnnotation(ApiResponseBody.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        return Collections.singletonList(apiKey);
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * 拦截 swagger 的js 内容，附加一段js，用于保存/读取 token with localStorage
     * @return
     */
    @Bean
    public FilterRegistrationBean hookSwaggerJsFile() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.addUrlPatterns("/swagger-ui/springfox.js");
        filterRegistrationBean.setFilter(swaggerJavascriptHookFilter);
        return filterRegistrationBean;
    }
}
