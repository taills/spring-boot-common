package com.github.taills.common.security.controller;

import com.github.taills.common.annotation.ApiResponseBody;
import com.github.taills.common.exception.ExceptionManager;
import com.github.taills.common.jpa.entity.SecurityGroup;
import com.github.taills.common.jpa.entity.SecurityRole;
import com.github.taills.common.jpa.entity.SecurityUser;
import com.github.taills.common.jpa.service.SecurityUserService;
import com.github.taills.common.response.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @ClassName AuthenticationController
 * @Description
 * @Author nil
 * @Date 2021/10/22 10:47 下午
 **/
@RestController
@ApiResponseBody
@RequestMapping("/security")
@Slf4j
@Api(tags = {"用户登录"})
public class AuthenticationController {

    @Autowired
    private SecurityUserService userService;


    @PostMapping("/login")
    @ApiOperation("登录")
    public ApiResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<SecurityUser> optional;
        optional = this.userService.verifyUsernameAndPassword(username, password);
        if (optional.isPresent()) {
            return ApiResult.success(this.userService.issueToken(optional.get()));
        } else {
            throw ExceptionManager.create(1001);
        }
    }

    @PostMapping("/admin/register")
    @ApiOperation(value = "注册管理员账号",notes = "仅在数据库为空时可以使用")
    @Transactional
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
