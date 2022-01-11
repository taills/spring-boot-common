package io.github.taills.common.jpa.service;

import io.github.taills.common.jpa.entity.SecurityUserRole;
import io.github.taills.common.jpa.repository.SecurityUserRoleRepository;

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
