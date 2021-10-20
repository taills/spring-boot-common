package com.gxmafeng.jpa.repository.impl;

import com.gxmafeng.jpa.repository.SecurityUserRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SecurityUser 自定义Repository实现层
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@Repository
public class SecurityUserRepositoryCustomImpl implements SecurityUserRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
}
