package com.gxmafeng;

import com.gxmafeng.jpa.entity.SecurityGroup;
import com.gxmafeng.jpa.entity.SecurityRole;
import com.gxmafeng.jpa.entity.SecurityUser;
import com.gxmafeng.jpa.service.SecurityGroupRoleService;
import com.gxmafeng.jpa.service.SecurityGroupService;
import com.gxmafeng.jpa.service.SecurityRoleService;
import com.gxmafeng.jpa.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityApp.class)
@Rollback
@Transactional
public class SecurityServiceTest {

    @Autowired
    SecurityGroupService groupService;

    @Autowired
    SecurityRoleService roleService;

    @Autowired
    SecurityGroupRoleService groupRoleService;

    @Autowired
    SecurityUserService userService;

    private Long adminGroupId;
    private Long userGroupId;

    private Long rootGroupId;

    private Long roleAdminId;
    private Long roleUserId;

    @Before
    public void setUp() throws Exception {
        log.info("新建角色 ROLE_ADMIN");
        SecurityRole roleAdmin = new SecurityRole();
        roleAdmin.setRoleName("ROLE_ADMIN");
        roleAdmin = roleService.save(roleAdmin);
        roleAdminId = roleAdmin.getId();

        SecurityGroup groupAdmin = new SecurityGroup();
        groupAdmin.setGroupName("超级管理员");
        groupAdmin.setParentId(187358314131095552L);
        List<SecurityRole> roles = new ArrayList<>();
        roles.add(roleAdmin);
        groupAdmin.setRoles(roles);
        groupAdmin = groupService.save(groupAdmin);
        adminGroupId = groupAdmin.getId();

        log.info("groupAdmin : {}", groupAdmin);


        log.info("新建角色 ROLE_USER");
        SecurityRole roleUser = new SecurityRole();
        roleUser.setRoleName("ROLE_USER");
        roleUser = roleService.save(roleUser);
        roleUserId = roleUser.getId();

        SecurityGroup groupUser = new SecurityGroup();
        groupUser.setGroupName("我是普通用户");
        groupUser.setParentId(groupAdmin.getId());
        List<SecurityRole> roles1 = new ArrayList<>();
        roles1.add(roleUser);
        groupUser.setRoles(roles1);
        groupUser = groupService.save(groupUser);
        log.info("groupUser : {}", groupUser);
        userGroupId = groupUser.getId();
        /**
         * 1、增加一个拥有2个角色的用户
         * 2、直接使用 groupService 保存带有角色关系的记录
         */
        SecurityGroup group = new SecurityGroup();
        group.setGroupName("ROOT");
        group.setParentId(187358314131095552L);
        List<SecurityRole> roleSet = new ArrayList<>();
        roleSet.add(roleService.getById(roleAdminId));
        roleSet.add(roleService.getById(roleUserId));
        group.setRoles(roleSet);
        SecurityGroup groupNew = groupService.save(group);
        log.info("ROOT : {}", groupNew);
        rootGroupId = groupNew.getId();

        SecurityUser user = new SecurityUser();
        user.setUsername("t_user");
        user.setNickname("测试用户哦");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("1234567890"));
        user.setMobile("13712345678");
        user.setAvatarUrl("http://localhost/123.png");
        //设置用户的角色
        List<SecurityRole> userRoles = new ArrayList<>();
        userRoles.add(roleService.getById(roleUserId));
        user.setRoles(userRoles);
        //设置用户的组
        List<SecurityGroup> userGroups = new ArrayList<>();
        userGroups.add(groupNew);
        user.setGroups(userGroups);

        user = userService.save(user);
        log.info("user　= {} ", user);
        assert user.getId() != null;
        assert user.getRoles().size() == 1;
        assert user.getGroups().size() == 1;

        groupService.findAll().forEach(item -> {
            log.info("group = {}", item);
        });
    }

    @After
    public void tearDown() throws Exception {
        log.info("tearDown");
    }


    @Test
    @Transactional
    public void testGetAndEdit() throws Exception {

        SecurityGroup admin = groupService.getById(adminGroupId);

        log.info("admin = {}", admin);
        log.info("adminRoles = {}", admin.getRoles());

        SecurityGroup user = groupService.getById(userGroupId);

        log.info("user = {}", user);
        log.info("userRoles = {}", user.getRoles());

        log.info("测试修改组名");
        log.info("user 旧名称 = {}", user.getGroupName());
        user.setGroupName("普通用户");
        groupService.save(user);
        user = groupService.getById(user.getId());
        log.info("user 新名称 = {}", user.getGroupName());
    }


    /**
     * 测试删除用户组及组的角色
     */
    @Test
    public void testDelete() {
        Optional<SecurityGroup> optional = groupService.findById(rootGroupId);
        if (optional.isPresent()) {
            log.info("admin = {}", optional.get());
            SecurityGroup group = optional.get();
            List<SecurityRole> roleList = group.getRoles();
            roleList.remove(0);
            group.setRoles(roleList);
            groupService.save(group);
            optional = groupService.findById(adminGroupId);
            assert optional.isPresent();
            log.info("size = {}", optional.get().getRoles().size());
            assert optional.get().getRoles().size() == 1;
        }
        Optional<SecurityGroup> optional2 = groupService.findById(userGroupId);
        if (optional.isPresent()) {
            log.info("user = {}", optional2.get());
            groupService.delete(optional2.get());
            optional2 = groupService.findById(userGroupId);
            assert optional2.isPresent() == false;
        }
    }


}