package com.github.taills.common.jpa.service;

import com.github.taills.common.jpa.entity.SecurityGroup;
import com.github.taills.common.jpa.repository.SecurityGroupRepository;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityGroup service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
public class SecurityGroupService extends AbstractService<SecurityGroup, String> {

	@Resource
	private SecurityGroupRepository rep;
}
