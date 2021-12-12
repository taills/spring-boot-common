package com.github.taills.common.security.controller;

import com.github.taills.common.jpa.entity.SecurityRole;
import com.github.taills.common.jpa.service.SecurityRoleService;
import com.github.taills.common.controller.BaseController;
import com.github.taills.common.annotation.ApiResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description
 * @Author nil
 * @Date 2021/10/20 11:41 下午
 **/
@ApiResponseBody
@RestController
@RequestMapping("/security/role")
public class RoleController extends BaseController<SecurityRole, Long> {
    public RoleController(SecurityRoleService service) {
        init(service);
    }
}
