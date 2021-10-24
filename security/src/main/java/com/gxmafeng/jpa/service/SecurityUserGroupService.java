package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityUserGroupRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityUserGroup;
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
