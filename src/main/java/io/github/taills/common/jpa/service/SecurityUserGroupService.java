package io.github.taills.common.jpa.service;

import io.github.taills.common.jpa.repository.SecurityUserGroupRepository;

import io.github.taills.common.jpa.entity.SecurityUserGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityUserGroup service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
public class SecurityUserGroupService extends AbstractService<SecurityUserGroup, String> {

	@Resource
	private SecurityUserGroupRepository rep;
}
