package com.gxmafeng.security.filter;

import com.gxmafeng.jpa.service.SecurityUserService;
import com.gxmafeng.security.userdetails.SecurityUserDetails;
import lombok.extern.slf4j.Slf4j;
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
        Optional<SecurityUserDetails> optionalSecurityUserDetails = this.securityUserService.parseToken(request.getHeader("Authorization"));
        if (optionalSecurityUserDetails.isPresent()) {
            //解析成功， setAuthentication 相关凭证
            SecurityContextHolder.getContext().setAuthentication(optionalSecurityUserDetails.get().getAuthentication());
        } else {
            log.debug("Token 解析失败");
        }
//        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            if (jwtTokenService.validateToken(jwtToken)) {
//                JwtUserInfo tokenClaims = jwtTokenService.getAllClaims(jwtToken);
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        new WechatUserDetails(tokenClaims.getUserId()), tokenClaims, null);
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                log.debug("[Token] {} {} {}", tokenClaims.getUserId(), tokenClaims.getUserType(), request.getRequestURL());
//                log.debug("[Token] {}", jwtToken);
//            }
//        } else {
////            log.debug("JWT Token does not begin with Bearer String");
//        }
        chain.doFilter(request, response);
    }

}
