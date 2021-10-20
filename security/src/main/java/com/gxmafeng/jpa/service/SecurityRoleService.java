package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityRoleRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityRole service层
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@Service
public class SecurityRoleService extends AbstractService<SecurityRole, Long> {

	@Resource
	private SecurityRoleRepository rep;
}
