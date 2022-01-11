package io.github.taills.common.util;

import io.github.taills.common.jpa.entity.SecurityUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * @ClassName TokenUtils
 * @Description
 * @Author nil
 * @Date 2021/12/18 6:24 PM
 **/
public class TokenUtils {
    /**
     * 默认的角色代码前缀
     */
    private static final String defaultRolePrefix = "ROLE_";

    /**
     * 从 HTTP 请求中获取凭证信息
     *
     * @return
     */
    public static SecurityUser getCurrentUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param roles
     * @return
     */
    public static boolean hasAnyRole(String... roles) {
        Set<String> roleSet = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        for (String role : roles) {
            if (roleSet.contains(defaultRolePrefix + role)) {
                return true;
            }
        }
        return false;
    }
}
