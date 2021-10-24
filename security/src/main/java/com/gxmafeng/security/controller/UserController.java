package com.gxmafeng.security.controller;

import com.gxmafeng.controller.BaseController;
import com.gxmafeng.jpa.entity.SecurityUser;
import com.gxmafeng.jpa.service.SecurityUserService;
import com.gxmafeng.annotation.ApiResponseBody;
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
public class UserController extends BaseController<SecurityUser, Long> {

    private final SecurityUserService securityUserService;

    public UserController(SecurityUserService service) {
        init(service);
        this.securityUserService = service;
    }
}
