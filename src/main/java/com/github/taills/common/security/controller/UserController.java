package com.github.taills.common.security.controller;

import com.github.taills.common.controller.BaseController;
import com.github.taills.common.jpa.entity.SecurityUser;
import com.github.taills.common.jpa.service.SecurityUserService;
import com.github.taills.common.annotation.ApiResponseBody;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description
 * @Author nil
 * @Date 2021/10/20 11:41 下午
 **/
@ApiResponseBody
@RestController
@RequestMapping("/security/user")
public class UserController extends BaseController<SecurityUser, Long> {

    private final SecurityUserService securityUserService;

    public UserController(SecurityUserService service) {
        init(service);
        this.securityUserService = service;
    }
}
