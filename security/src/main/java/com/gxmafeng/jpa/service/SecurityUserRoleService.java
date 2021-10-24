package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityUserRoleRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityUserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityUserRole service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
public class SecurityUserRoleService extends AbstractService<SecurityUserRole, String> {

	@Resource
	private SecurityUserRoleRepository rep;
}
