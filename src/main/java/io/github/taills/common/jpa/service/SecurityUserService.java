package io.github.taills.common.jpa.service;

import io.github.taills.common.jpa.entity.SecurityUser;
import io.github.taills.common.jpa.repository.SecurityUserRepository;
import io.github.taills.common.security.jti.JtiService;
import io.github.taills.common.security.properties.SecurityProperties;
import io.github.taills.common.security.userdetails.SecurityUserDetails;
import io.github.taills.common.util.SnowFlake;
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

    private final SecurityProperties securityProperties;

    private final PasswordEncoder passwordEncoder;

    private final JtiService jtiService;

    private final String tokenPrefix = "Bearer ";

    public SecurityUserService(SecurityProperties securityProperties, PasswordEncoder passwordEncoder, JtiService jtiService) {
        this.passwordEncoder = passwordEncoder;
        if (null == securityProperties.getKey() || securityProperties.getKey().isEmpty()) {
            securityProperties = new SecurityProperties();
            securityProperties.setKey("this is default jwt key.");
            securityProperties.setLifeTime(60 * 60 * 24);
        }
        this.securityProperties = securityProperties;
        this.jtiService = jtiService;
    }

    /**
     * JPA Repository
     */
    @Resource
    private SecurityUserRepository rep;

    @Autowired
    private SecurityRoleService securityRoleService;


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
        return this.verifyPassword(findByUsername(username), rawPassword);
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
        Date exp = new Date(System.currentTimeMillis() + securityProperties.getLifeTime() * 1000);
        Date now = new Date(System.currentTimeMillis());
        Set<String> authorities = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet());
        String token = Jwts.builder().setSubject(userDetails.getUsername())
                .claim("authorities", String.join(",", authorities))
                .setIssuedAt(now)
                .setIssuer("JWT")
                .setExpiration(exp)
                .setId(SnowFlake.get().nextSid())
                .setExpiration(exp).signWith(SignatureAlgorithm.HS512, securityProperties.getKey()).compact();
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
            String token = httpHeaderToken.substring(7);
            try {
                // token 过期的话，在这一步会抛出一个异常
                Claims claims = Jwts.parser().setSigningKey(securityProperties.getKey()).parseClaimsJws(token).getBody();
                // 所以就不检测这个条件： claims.getExpiration().after(now)
                if (claims.getIssuedAt().before(now)) {
                    //校验 jti 是否被撤销了
                    if (!jtiService.isRevoked(claims.getId())) {
                        //查找
                        Optional<SecurityUser> optionalSecurityUser = findByUsername(claims.getSubject());
                        if (optionalSecurityUser.isPresent()) {
                            return Optional.of(SecurityUserDetails.fromSecurityUser(optionalSecurityUser.get()));
                        }
                    } else {
                        log.debug("token 已被撤销。 {}", claims.getId());
                    }
                } else {
                    log.debug("token 还未生效，生效时间为 {} 之后。 {}", claims.getIssuedAt(), token);
                }
            } catch (io.jsonwebtoken.ExpiredJwtException expiredJwtException) {
                log.debug("token 已过期。 {}", token);
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

    /**
     * 把用户注册为管理员
     *
     * @param user
     * @return
     */
    public SecurityUser registerAdmin(SecurityUser user) {
        user.setRoles(new HashSet<>(securityRoleService.getAdminRoles()));
        return save(user);
    }
}
