package com.gxmafeng.jpa.service;

import com.gxmafeng.jpa.entity.SecurityRole;
import com.gxmafeng.jpa.repository.SecurityUserRepository;
import com.gxmafeng.jpa.service.AbstractService;
import com.gxmafeng.jpa.entity.SecurityUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 获取用户所有的角色并集,包括用户角色，所载组及子组的角色
     *
     * @return
     */
    private List<SecurityRole> getAllRoles(SecurityUser user) {
        List<SecurityRole> roles = user.getRoles();
        return roles;
    }

    public Boolean hasRole(String roleName) {

        return false;
    }
}
