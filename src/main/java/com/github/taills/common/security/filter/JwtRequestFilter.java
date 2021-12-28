package com.github.taills.common.security.filter;

import com.github.taills.common.jpa.service.SecurityUserService;
import com.github.taills.common.security.userdetails.SecurityUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author taills
 * Create On 2020/6/4 10:02 下午
 */

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityUserService securityUserService;

    public JwtRequestFilter(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        Optional<SecurityUserDetails> optionalSecurityUserDetails = this.securityUserService.parseToken(token);
        if (optionalSecurityUserDetails.isPresent()) {
            //解析成功， setAuthentication 相关凭证
            Authentication authentication = optionalSecurityUserDetails.get().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.debug("Token 解析失败");
        }
        chain.doFilter(request, response);
    }

}
