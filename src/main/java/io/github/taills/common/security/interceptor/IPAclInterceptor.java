package io.github.taills.common.security.interceptor;

import io.github.taills.common.exception.ExceptionManager;
import io.github.taills.common.jpa.service.SecurityIpAclService;
import io.github.taills.common.security.properties.CommonSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName IPAclInterceptor
 * @Description
 * @Author nil
 * @Date 2022/1/18 12:31 AM
 **/
@Slf4j
@Component
public class IPAclInterceptor implements HandlerInterceptor {
    @Autowired
    private CommonSecurityProperties commonSecurityProperties;

    @Autowired
    private SecurityIpAclService ipAclService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (commonSecurityProperties.isIpAclUseXForwardedFor() && StringUtils.isNotBlank(request.getHeader("x-forwarded-for"))) {
            String xForwardFor = request.getHeader("x-forwarded-for");
            if (xForwardFor.indexOf(",") > 0) {
                checkIp(xForwardFor.split(",")[0]);
            } else {
                checkIp(xForwardFor);
            }
        } else {
            checkIp(request.getRemoteAddr());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void checkIp(String ip) {
        log.debug("check {}", ip);
        //优先 allow list，存在则放行
        //其次 deny，存在则拦截
        if (!ipAclService.inAllowList(ip) && ipAclService.inDenyList(ip)) {
            throw ExceptionManager.create(1006);
        }
    }
}
