package com.github.taills.common.filter;


import com.github.taills.common.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SwaggerJavascriptHookFilter
 * @Description
 * @Author nil
 * @Date 2022/1/11 2:06 PM
 **/
@Slf4j
@Component
public class SwaggerJavascriptHookFilter implements Filter {

    @Value("classpath:/springfox-append.js")
    private Resource springfoxAppendJs;

    @Override
    public void doFilter(ServletRequest request, ServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper((HttpServletResponse) httpServletResponse);
        filterChain.doFilter(request, response);
        int contentLength = response.getContentSize();
        String contentSwaggerAppendJs = ResourceUtils.asString(springfoxAppendJs);
        contentLength += contentSwaggerAppendJs.length();
        if (null != contentSwaggerAppendJs) {
            response.getWriter().write(contentSwaggerAppendJs);
        }
        response.setContentLength(contentLength);
        response.copyBodyToResponse();
    }
}
