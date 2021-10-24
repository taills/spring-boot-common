package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.repository.SecurityApiLogRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityApiLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecurityApiLog service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
public class SecurityApiLogService extends AbstractService<SecurityApiLog, String> {

	@Resource
	private SecurityApiLogRepository rep;
}
