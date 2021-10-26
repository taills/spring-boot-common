package com.gxmafeng.security.userdetails;

import com.gxmafeng.jpa.entity.SecurityUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @ClassName SecurityUser
 * @Description
 * @Author nil
 * @Date 2021/10/22 11:42 下午
 **/
public class SecurityUserDetails implements UserDetails {

    private SecurityUser securityUser;

    private String token;

    private List<GrantedAuthority> authorities;

    private final String rolePrefix = "ROLE_";

    public Authentication getAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.securityUser.getUsername(), this.token, this.getAuthorities());
    }

    /**
     * 从 SecurityUser 转换为 SecurityUserDetails
     *
     * @param securityUser
     * @return
     */
    public static SecurityUserDetails fromSecurityUser(SecurityUser securityUser) {
        SecurityUserDetails userDetails = new SecurityUserDetails();
        userDetails.setSecurityUser(securityUser);
        return userDetails;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities == null) {
            this.authorities = new ArrayList<>();
            this.securityUser.getRoles().forEach(securityRole -> {
                String roleName = securityRole.getRoleName();
                if (!roleName.startsWith(rolePrefix)) {
                    roleName = rolePrefix + roleName;
                }
                this.authorities.add(new SimpleGrantedAuthority(roleName));
            });
        }
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return securityUser.getPassword();
    }

    @Override
    public String getUsername() {
        return securityUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return (new Date()).before(securityUser.getExpiredAt());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !securityUser.getIsLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return securityUser.getIsEnabled();
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
