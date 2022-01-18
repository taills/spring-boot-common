package io.github.taills.common.security.controller;

import io.github.taills.common.controller.BaseController;
import io.github.taills.common.jpa.entity.SecurityGroup;
import io.github.taills.common.jpa.service.SecurityGroupService;
import io.github.taills.common.annotation.ApiResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Description
 * @Author nil
 * @Date 2021/10/20 11:41 下午
 **/
@RestController
@ApiResponseBody
@RequestMapping("/security/group")
public class SecurityGroupController extends BaseController<SecurityGroup, String> {
    public SecurityGroupController(SecurityGroupService service) {
        init(service);
    }
}
