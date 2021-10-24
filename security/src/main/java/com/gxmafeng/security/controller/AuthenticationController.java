package com.gxmafeng.security.controller;

import com.gxmafeng.annotation.ApiResponseBody;
import com.gxmafeng.jpa.entity.SecurityUser;
import com.gxmafeng.jpa.service.SecurityUserService;
import com.gxmafeng.response.ApiResult;
import com.gxmafeng.security.model.VoUserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResult login(@RequestBody VoUserLogin voUserLogin) {
        Optional<SecurityUser> optional = Optional.empty();
        if (!voUserLogin.getUsername().isEmpty() && !voUserLogin.getPassword().isEmpty()) {
            optional = this.userService.verifyUsernameAndPassword(voUserLogin.getUsername(), voUserLogin.getPassword());
        } else if (!voUserLogin.getMobile().isEmpty() && !voUserLogin.getPassword().isEmpty()) {
            optional = this.userService.verifyMobileAndPassword(voUserLogin.getMobile(), voUserLogin.getPassword());
        }
        log.debug("optional = {}", optional);
        if (optional.isPresent()) {
            return ApiResult.success(this.userService.issueToken(optional.get()));
        } else {
            return ApiResult.failure();
        }
    }
}
