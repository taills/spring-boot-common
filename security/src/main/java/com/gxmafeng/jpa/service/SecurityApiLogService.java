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
 * @date 2021-10-20 15:44:55
 */
@Service
public class SecurityApiLogService extends AbstractService<SecurityApiLog, Long> {

	@Resource
	private SecurityApiLogRepository rep;
}
