package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityGroupRoleRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityGroupRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityGroupRole service层
 *
 * @author auto generated
 * @date 2021-10-20 16:37:26
 */
@Service
public class SecurityGroupRoleService extends AbstractService<SecurityGroupRole, Long> {

	@Resource
	private SecurityGroupRoleRepository rep;
}
