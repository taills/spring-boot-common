package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityGroupRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityGroup service层
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@Service
public class SecurityGroupService extends AbstractService<SecurityGroup, Long> {

	@Resource
	private SecurityGroupRepository rep;
}
