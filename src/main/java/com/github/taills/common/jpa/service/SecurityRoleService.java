package com.github.taills.common.jpa.service;

import com.github.taills.common.jpa.repository.SecurityRoleRepository;

import com.github.taills.common.jpa.entity.SecurityRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityRole service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
public class SecurityRoleService extends AbstractService<SecurityRole, String> {

	@Resource
	private SecurityRoleRepository rep;
}
