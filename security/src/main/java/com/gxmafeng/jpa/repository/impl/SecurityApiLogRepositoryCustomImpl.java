package com.gxmafeng.jpa.repository.impl;

import com.gxmafeng.jpa.repository.SecurityApiLogRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SecurityApiLog 自定义Repository实现层
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@Repository
public class SecurityApiLogRepositoryCustomImpl implements SecurityApiLogRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
}
