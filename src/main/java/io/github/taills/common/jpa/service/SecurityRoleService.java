package io.github.taills.common.jpa.service;

import io.github.taills.common.jpa.repository.SecurityRoleRepository;

import io.github.taills.common.jpa.entity.SecurityRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * SecurityRole service层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@Service
@Slf4j
public class SecurityRoleService extends AbstractService<SecurityRole, String> {

    @Resource
    private SecurityRoleRepository rep;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        log.debug("SecurityRoleService 初始化");
        this.insertIfNotExists("ADMIN_READ", "管理员-只读");
        this.insertIfNotExists("ADMIN_CREATE", "管理员-创建");
        this.insertIfNotExists("ADMIN_UPDATE", "管理员-更新");
        this.insertIfNotExists("ADMIN_DELETE", "管理员-删除");
    }

    /**
     * 当 roleName 不存在时插入
     *
     * @param roleName
     * @param description
     */
    @Transactional
    public void insertIfNotExists(String roleName, String description) {
        if (rep.countAllByRoleName(roleName) == 0) {
            SecurityRole securityRole = new SecurityRole();
            securityRole.setRoleName(roleName);
            securityRole.setDescription(description);
            securityRole = this.save(securityRole);
            log.debug("插入 SecurityRole {} {}", roleName, description);
        }
    }

    /**
     * 获取管理员的角色
     * @return
     */
    public List<SecurityRole> getAdminRoles(){
        SecurityRole securityRole = new SecurityRole();
        securityRole.setRoleName("ADMIN");
        ExampleMatcher stringMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
        return rep.findAll(Example.of(securityRole,stringMatcher));
    }
}
