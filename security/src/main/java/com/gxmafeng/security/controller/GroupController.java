package com.gxmafeng.security.controller;

import com.gxmafeng.controller.BaseController;
import com.gxmafeng.jpa.entity.SecurityGroup;
import com.gxmafeng.jpa.service.SecurityGroupService;
import com.gxmafeng.annotation.ApiResponseBody;
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
public class GroupController extends BaseController<SecurityGroup, Long> {
    public GroupController(SecurityGroupService service) {
        init(service);
    }
}
