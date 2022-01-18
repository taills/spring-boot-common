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

    /**
     * 检查 IP 。
     * 代码检查器说不要再判断里写复杂的语句，另一个也是方便阅读，把条件判断拆分得很细
     * @param ip
     */
    private void checkIp(String ip) {
        //判断 allow 和 deny 哪个优先
        if (commonSecurityProperties.isIpAclPriorityAllow()) {
            // allow 优先
            if (ipAclService.inAllowList(ip)) {
                //存在 allow 记录，直接返回，放行
                return;
            }
            // 不存在 allow 记录，则继续往下走
            if (ipAclService.inDenyList(ip)) {
                // 存在 deny 记录，拦截
                throw ExceptionManager.create(1006);
            } else {
                // 默认策略
                if (!commonSecurityProperties.isIpAclDefaultAllow()) {
                    // 默认策略是 deny，拦截
                    throw ExceptionManager.create(1006);
                }
            }
        } else {
            // deny 优先
            if (ipAclService.inDenyList(ip)) {
                //存在 deny，则拦截
                throw ExceptionManager.create(1006);
            }else {
                // 不存在 deny
                if (!commonSecurityProperties.isIpAclDefaultAllow() && !ipAclService.inAllowList(ip)){
                    // 默认策略是 deny，且ip又不处于 allow 列表中，拦截
                    throw ExceptionManager.create(1006);
                }

            }
        }
    }
}
