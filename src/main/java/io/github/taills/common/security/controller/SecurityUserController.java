package io.github.taills.common.security.controller;

import io.github.taills.common.controller.BaseController;
import io.github.taills.common.jpa.entity.SecurityUser;
import io.github.taills.common.jpa.service.SecurityUserService;
import io.github.taills.common.annotation.ApiResponseBody;
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
public class SecurityUserController extends BaseController<SecurityUser, Long> {

    private final SecurityUserService securityUserService;

    public SecurityUserController(SecurityUserService service) {
        init(service);
        this.securityUserService = service;
    }
}
