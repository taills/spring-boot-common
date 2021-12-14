package com.github.taills.common.security.controller;

import com.github.taills.common.annotation.ApiResponseBody;
import com.github.taills.common.jpa.entity.SecurityUser;
import com.github.taills.common.jpa.service.SecurityUserService;
import com.github.taills.common.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
public class AuthenticationController {

    @Autowired
    private SecurityUserService userService;

    @PostMapping("/login")
    public ApiResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<SecurityUser> optional = Optional.empty();
        optional = this.userService.verifyUsernameAndPassword(username, password);
        log.debug("optional = {}", optional);
        if (optional.isPresent()) {
            return ApiResult.success(this.userService.issueToken(optional.get()));
        } else {
            return ApiResult.failure();
        }
    }
}