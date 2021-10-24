package com.gxmafeng.security.controller;

import com.gxmafeng.controller.BaseController;
import com.gxmafeng.jpa.entity.SecurityRole;
import com.gxmafeng.jpa.service.SecurityRoleService;
import com.gxmafeng.annotation.ApiResponseBody;
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
