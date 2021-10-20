package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityUserRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityUser service层
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@Service
public class SecurityUserService extends AbstractService<SecurityUser, Long> {

	@Resource
	private SecurityUserRepository rep;
}
