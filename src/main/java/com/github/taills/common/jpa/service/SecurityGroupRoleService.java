package com.github.taills.common.jpa.service;

import com.github.taills.common.jpa.repository.SecurityGroupRoleRepository;

import com.github.taills.common.jpa.entity.SecurityGroupRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityGroupRole service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
public class SecurityGroupRoleService extends AbstractService<SecurityGroupRole, String> {

	@Resource
	private SecurityGroupRoleRepository rep;
}
