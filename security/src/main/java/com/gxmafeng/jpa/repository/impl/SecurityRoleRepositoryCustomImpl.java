package com.gxmafeng.jpa.repository.impl;

import com.gxmafeng.jpa.repository.SecurityRoleRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SecurityRole 自定义Repository实现层
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@Repository
public class SecurityRoleRepositoryCustomImpl implements SecurityRoleRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
}
