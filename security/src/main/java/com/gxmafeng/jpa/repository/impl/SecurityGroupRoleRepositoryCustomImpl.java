package com.gxmafeng.jpa.repository.impl;

import com.gxmafeng.jpa.repository.SecurityGroupRoleRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SecurityGroupRole 自定义Repository实现层
 *
 * @author auto generated
 * @date 2021-10-20 16:37:26
 */
@Repository
public class SecurityGroupRoleRepositoryCustomImpl implements SecurityGroupRoleRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
}
