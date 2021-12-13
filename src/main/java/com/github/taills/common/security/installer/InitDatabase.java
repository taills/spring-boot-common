package com.github.taills.common.security.installer;

import com.github.taills.common.jpa.entity.SecurityRole;
import com.github.taills.common.jpa.service.SecurityGroupRoleService;
import com.github.taills.common.jpa.service.SecurityRoleService;
import com.github.taills.common.jpa.entity.SecurityGroup;
import com.github.taills.common.jpa.entity.SecurityUser;
import com.github.taills.common.jpa.service.SecurityGroupService;
import com.github.taills.common.jpa.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 初始化数据库
 *
 * @ClassName InitDatabase
 * @Description
 * @Author nil
 * @Date 2021/10/24 4:20 下午
 **/
@Service
public class InitDatabase {

    final String[] ROLE_NAMES = {
            "ADMIN_READ", "ADMIN_CREATE", "ADMIN_UPDATE", "ADMIN_DELETE",
            "USER_READ", "USER_CREATE", "USER_UPDATE", "USER_DELETE",
            "READ"
    };


    @Autowired
    SecurityGroupService groupService;

    @Autowired
    SecurityRoleService roleService;

    @Autowired
    SecurityGroupRoleService groupRoleService;

    @Autowired
    SecurityUserService userService;

    @SuppressWarnings("AlibabaMethodTooLong")
    public void init() {
        //角色
        Set<SecurityRole> adminRoles = new HashSet<>();
        Set<SecurityRole> userRoles = new HashSet<>();
        Set<SecurityRole> otherRoles = new HashSet<>();

        for (int i = 0; i < ROLE_NAMES.length; i++) {
            SecurityRole role = new SecurityRole();
            role.setRoleName(ROLE_NAMES[i]);
            role.setDescription("角色" + ROLE_NAMES[i]);
            role = this.roleService.save(role);
            if (ROLE_NAMES[i].startsWith("ADMIN")) {
                adminRoles.add(role);
            } else if (ROLE_NAMES[i].startsWith("USER")) {
                userRoles.add(role);
            } else {
                otherRoles.add(role);
            }
        }

        //用户组

        SecurityGroup rootGroup = new SecurityGroup();
        rootGroup.setGroupName("超级管理员");
        rootGroup = this.groupService.save(rootGroup);

        SecurityGroup adminGroup = new SecurityGroup();
        adminGroup.setGroupName("管理员");
        adminGroup.setRoles(adminRoles);
        adminGroup.setParentId(rootGroup.getId());
        adminGroup = this.groupService.save(adminGroup);


        SecurityGroup userGroup = new SecurityGroup();
        userGroup.setParentId(adminGroup.getId());
        userGroup.setGroupName("普通用户");
        userGroup.setRoles(userRoles);
        userGroup = this.groupService.save(userGroup);

        //新建 4 个用户，分别属于3个用户组 + 1 无分组，只给用户 READ 角色

        SecurityUser uEmpty = new SecurityUser();

        uEmpty.setUsername("empty");
        uEmpty.setPassword("123456");
        uEmpty.setNickname("空用户");
        uEmpty.setRoles(otherRoles);

        this.userService.save(uEmpty);


        SecurityUser uRoot = new SecurityUser();
        uRoot.setUsername("root");
        uRoot.setPassword("123456");
        uRoot.setNickname("Root");
        uRoot.setRoles(otherRoles);
        Set<SecurityGroup> rootGroups = new HashSet<>();
        rootGroups.add(rootGroup);
        uRoot.setGroups(rootGroups);
        this.userService.save(uRoot);


        SecurityUser uAdmin = new SecurityUser();

        uAdmin.setUsername("admin");
        uAdmin.setPassword("123456");
        uAdmin.setNickname("Admin");
        uAdmin.setRoles(otherRoles);
        Set<SecurityGroup> rootAdmin = new HashSet<>();
        rootAdmin.add(adminGroup);
        uAdmin.setGroups(rootAdmin);
        this.userService.save(uAdmin);


        SecurityUser uUser = new SecurityUser();

        uUser.setUsername("user");
        uUser.setPassword("123456");
        uUser.setNickname("User");
        uUser.setRoles(otherRoles);
        Set<SecurityGroup> g = new HashSet<>();
        g.add(userGroup);
        uUser.setGroups(g);
        this.userService.save(uUser);

    }
}
