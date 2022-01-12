package io.github.taills.common.security.controller;

import io.github.taills.common.controller.BaseController;
import io.github.taills.common.jpa.entity.SecurityUser;
import io.github.taills.common.jpa.service.SecurityUserService;
import io.github.taills.common.annotation.ApiResponseBody;
import io.github.taills.common.security.jti.JtiService;
import io.github.taills.common.response.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "SecurityUser")
public class SecurityUserController extends BaseController<SecurityUser, Long> {

    private final SecurityUserService securityUserService;

    @Autowired
    private final JtiService jtiService;

    public SecurityUserController(SecurityUserService service,JtiService jtiService) {
        init(service);
        this.securityUserService = service;
        this.jtiService = jtiService;
    }

    @PostMapping("/admin/revoke")
    @ApiOperation(value = "撤销 Token",notes = "撤销Token")
    @PreAuthorize("hasRole('ADMIN_UPDATE')")
    public ApiResult revokeToken(@RequestParam("jti") String jti) {
        jtiService.revoke(jti);
        return ApiResult.success();
    }
}
