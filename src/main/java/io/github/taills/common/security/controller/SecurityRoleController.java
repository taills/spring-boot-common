package io.github.taills.common.security.controller;

import io.github.taills.common.jpa.entity.SecurityRole;
import io.github.taills.common.jpa.service.SecurityRoleService;
import io.github.taills.common.controller.BaseController;
import io.github.taills.common.annotation.ApiResponseBody;
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
public class SecurityRoleController extends BaseController<SecurityRole, String> {
    public SecurityRoleController(SecurityRoleService service) {
        init(service);
    }
}
