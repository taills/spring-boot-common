package com.github.taills.common.jpa.service;

import com.github.taills.common.jpa.repository.SecurityUserRepository;
import com.github.taills.common.jpa.entity.SecurityUser;

import com.github.taills.common.security.userdetails.SecurityUserDetails;
import com.github.taills.common.util.SnowFlake;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SecurityUser service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "SecurityUserService")
public class SecurityUserService extends AbstractService<SecurityUser, String> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String tokenPrefix = "Bearer ";

    /**
     * JWT 私钥
     */
    private final String JWT_KEY = "wcgEg550LUsrsvas7SVXkE5bwo9zcG7eB0wBxHlW5XK3Xep9AYsvO8wuxekhpbzwmlJXxr2WCrBhlrXt3R0";
    /**
     * JWT 有效期 毫秒
     */
    private final int JWT_INDATE = 1000 * 60 * 60 * 24;

    /**
     * JPA Repository
     */
    @Resource
    private SecurityUserRepository rep;

    /**
     * 注入自己的原因在于，直接使用 this 调用方法，无法触发 @Cacheable 注解的缓存功能
     */
    @Autowired
    private SecurityUserService self;


    @Cacheable(key = "'U-' + #username")
    public Optional<SecurityUser> findByUsername(String username) {
        return rep.findByUsername(username);
    }

    /**
     * 校验 SecurityUser 对象的密码
     *
     * @param optionalSecurityUser
     * @param rawPassword
     * @return
     */
    private Optional<SecurityUser> verifyPassword(Optional<SecurityUser> optionalSecurityUser, String rawPassword) {
        if (optionalSecurityUser.isPresent()) {
            if (passwordEncoder.matches(rawPassword, optionalSecurityUser.get().getPassword())) {
                return optionalSecurityUser;
            }
        }
        return Optional.empty();
    }

    /**
     * 校验用户名 + 密码
     *
     * @param username
     * @param rawPassword
     * @return
     */
    public Optional<SecurityUser> verifyUsernameAndPassword(String username, String rawPassword) {
        return this.verifyPassword(self.findByUsername(username), rawPassword);
    }

    /**
     * 根据用户名颁发令牌
     *
     * @param username
     * @return
     */
    public String issueToken(String username) {
        Optional<SecurityUser> optionalSecurityUser = this.findByUsername(username);
        if (optionalSecurityUser.isPresent()) {
            return issueToken(optionalSecurityUser.get());
        } else {
            return null;
        }
    }

    /**
     * 颁发令牌
     *
     * @param securityUser
     * @return
     */
    public String issueToken(SecurityUser securityUser) {
        return issueToken(SecurityUserDetails.fromSecurityUser(securityUser));
    }

    /**
     * 颁发令牌
     *
     * @param userDetails
     * @return
     */
    public String issueToken(SecurityUserDetails userDetails) {
        Date exp = new Date(System.currentTimeMillis() + JWT_INDATE);
        Date now = new Date(System.currentTimeMillis());
        Set<String> authorities = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet());
        String token = Jwts.builder().setSubject(userDetails.getUsername())
                .claim("authorities", String.join(",", authorities))
                .setIssuedAt(now)
                .setIssuer("JWT")
                .setExpiration(exp)
                .setId(SnowFlake.get().nextSid())
                .setExpiration(exp).signWith(SignatureAlgorithm.HS512, JWT_KEY).compact();
        return token;
    }

    /**
     * 解析令牌
     *
     * @param httpHeaderToken
     * @return
     */
    public Optional<SecurityUserDetails> parseToken(String httpHeaderToken) {
        Date now = new Date();
        if (httpHeaderToken != null && httpHeaderToken.length() > tokenPrefix.length() && httpHeaderToken.startsWith(tokenPrefix)) {
            try {
                String token = httpHeaderToken.substring(7);
                Claims claims = Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token).getBody();
                if (claims.getIssuedAt().before(now) && claims.getExpiration().after(now)) {
                    //校验 jti
                    //claims.getId()
                    log.debug("JTI {}", claims.getId());
                    //查找
                    Optional<SecurityUser> optionalSecurityUser = self.findByUsername(claims.getSubject());
                    if (optionalSecurityUser.isPresent()) {
                        return Optional.of(SecurityUserDetails.fromSecurityUser(optionalSecurityUser.get()));
                    }
                }
            } catch (Exception e) {
                log.debug("Token 解析失败 {}", e.getLocalizedMessage());
            }

        }
        return Optional.empty();
    }


    @Override
    public <S extends SecurityUser> S save(S entity) {
        if (entity.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        return super.save(entity);
    }

    @Override
    public <S extends SecurityUser> List<S> saveAll(Iterable<S> entities) {
        List<S> list = new ArrayList<>();
        entities.forEach(entity -> {
            if (entity.getPassword() != null) {
                entity.setPassword(passwordEncoder.encode(entity.getPassword()));
                list.add(entity);
            }
        });
        return super.saveAll(list);
    }
}
