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
 * @date 2021-10-20 16:37:26
 */
@Service
public class SecurityUserRoleService extends AbstractService<SecurityUserRole, Long> {

	@Resource
	private SecurityUserRoleRepository rep;
}
