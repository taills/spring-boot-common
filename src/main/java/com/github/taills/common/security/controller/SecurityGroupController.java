package com.github.taills.common.security.controller;

import com.github.taills.common.controller.BaseController;
import com.github.taills.common.jpa.entity.SecurityGroup;
import com.github.taills.common.jpa.service.SecurityGroupService;
import com.github.taills.common.annotation.ApiResponseBody;
import io.swagger.annotations.Api;
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
public class SecurityGroupController extends BaseController<SecurityGroup, Long> {
    public SecurityGroupController(SecurityGroupService service) {
        init(service);
    }
}
