package io.github.taills.common.jpa.controller;

import io.github.taills.common.jpa.service.SecurityIpAclService;
import io.github.taills.common.annotation.ApiResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.github.taills.common.controller.BaseController;
import io.github.taills.common.jpa.entity.SecurityIpAcl;

/**
 * SecurityIpAcl Controller层
 *
 * @author automatically generated by taills's tool
 * @date 2022-01-17 20:16:59
 */
@ApiResponseBody
@RestController
@RequestMapping("/SecurityIpAcl")
@Api(tags = "SecurityIpAcl")
public class SecurityIpAclController extends BaseController<SecurityIpAcl, String> {

	public SecurityIpAclController(SecurityIpAclService service) {
		init(service);
	}
}
