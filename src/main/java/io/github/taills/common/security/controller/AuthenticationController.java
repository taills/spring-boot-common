package io.github.taills.common.security.controller;

import io.github.taills.common.annotation.ApiResponseBody;
import io.github.taills.common.exception.ExceptionManager;
import io.github.taills.common.jpa.entity.SecurityUser;
import io.github.taills.common.jpa.service.SecurityUserService;
import io.github.taills.common.limiter.Limit;
import io.github.taills.common.response.ApiResult;
import io.github.taills.common.security.properties.SecurityProperties;
import io.github.taills.common.security.support.model.response.TokenChallenge;
import io.github.taills.common.support.GoogleAuthenticator;
import io.github.taills.common.util.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName AuthenticationController
 * @Description
 * @Author nil
 * @Date 2021/10/22 10:47 下午
 **/
@ApiResponseBody
@RequestMapping("/security")
@Slf4j
@Api(tags = {"用户登录"})
public class AuthenticationController {

    @Autowired
    private SecurityUserService userService;

    @Autowired
    private SecurityProperties securityProperties;


    @PostMapping("/login")
    @ApiOperation("登录")
    @Limit(permitsPerSecond = 0.5)
    public ApiResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<SecurityUser> optionalSecurityUser;
        TokenChallenge tokenChallenge = new TokenChallenge();
        optionalSecurityUser = this.userService.verifyUsernameAndPassword(username, password);
        if (optionalSecurityUser.isPresent()) {
            SecurityUser user = optionalSecurityUser.get();
            //默认给空角色 token
            tokenChallenge.setToken(userService.issueEmptyAuthorityToken(username));
            if (securityProperties.isMandatoryTwoStepAuthentication()) {
                // 强制两步认证，要求用户必须绑定两步认证
                if (StringUtils.isBlank(user.getTotpSecret())) {
                    // 未绑定，要求绑定
                    tokenChallenge.setNextStep("bind");
                } else {
                    // 已绑定，要求认证
                    tokenChallenge.setNextStep("challenge");
                }
            } else {
                // 未开启强制两步认证，用户可自愿选择是否绑定
                if (StringUtils.isBlank(user.getTotpSecret())) {
                    // 用户未绑定，直接返回正常角色 token
                    tokenChallenge.setToken(userService.issueToken(username));
                } else {
                    // 用户已绑定，要求认证
                    tokenChallenge.setNextStep("challenge");
                }
            }
            return ApiResult.success(tokenChallenge);
        } else {
            throw ExceptionManager.create(1001);
        }
    }

    @PostMapping("/challenge")
    @ApiOperation(value = "两步认证校验，换取 token")
    @Limit(permitsPerSecond = 0.5)
    public ApiResult twoFactorChallenge(@RequestParam("code") Long code) {
        Optional<SecurityUser> optionalSecurityUser;
        optionalSecurityUser = userService.findByUsername(TokenUtils.getCurrentUser().getUsername());
        if (optionalSecurityUser.isPresent()) {
            if (GoogleAuthenticator.verifyCode(optionalSecurityUser.get().getTotpSecret(), code)) {
                return ApiResult.success(userService.issueToken(optionalSecurityUser.get().getUsername()));
            }
            throw ExceptionManager.create(1004);
        } else {
            throw ExceptionManager.create(1003);
        }
    }

    @GetMapping("/bind")
    @ApiOperation(value = "获取两步认证的secret 和 totp url")
    @Limit(permitsPerSecond = 0.5)
    public ApiResult getTwoFactorBindData() {
        String secret = GoogleAuthenticator.generateSecretKey();
        TokenChallenge tokenChallenge = new TokenChallenge();
        tokenChallenge.setSecret(secret);
        tokenChallenge.setTotpUrl(GoogleAuthenticator.makeUrl(securityProperties.getTwoSetpIssuer(), TokenUtils.getCurrentUser().getUsername(), secret));
        return ApiResult.success(tokenChallenge);
    }

    @PostMapping("/bind")
    @ApiOperation(value = "两步认证绑定")
    @Limit(permitsPerSecond = 0.5)
    public ApiResult twoFactorBind(@RequestParam("code") Long code, @RequestParam("secret") String secret) {
        if (secret.length() != GoogleAuthenticator.getSecretStringLength()) {
            log.error("2FA绑定异常，secret 长度不对。{}", secret);
            throw ExceptionManager.create(1003);
        }
        if (GoogleAuthenticator.verifyCode(secret, code)) {
            // 把 secret 存到数据库
            Optional<SecurityUser> optionalSecurityUser = userService.findByUsername(TokenUtils.getCurrentUser().getUsername());
            if (optionalSecurityUser.isPresent()) {
                SecurityUser securityUser = optionalSecurityUser.get();
                if (StringUtils.isBlank(securityUser.getTotpSecret())) {
                    securityUser.setTotpSecret(secret);
                    userService.save(securityUser);
                    return ApiResult.success();
                } else {
                    throw ExceptionManager.create(1005);
                }
            }
        }
        return ApiResult.failure();
    }

    @PostMapping("/admin/register")
    @ApiOperation(value = "注册管理员账号", notes = "仅在数据库为空时可以使用")
    @Transactional
    @Limit(permitsPerSecond = 0.1)
    public ApiResult registerAdmin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (userService.count() > 0) {
            throw ExceptionManager.create(1002);
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(username);
        securityUser.setPassword(password);
        securityUser.setIsEnabled(true);
        securityUser.setNickname(username);
        return ApiResult.success(userService.registerAdmin(securityUser));
    }
}
