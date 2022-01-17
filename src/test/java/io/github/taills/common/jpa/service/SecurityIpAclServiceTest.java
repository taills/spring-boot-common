package io.github.taills.common.jpa.service;

import io.github.taills.common.jpa.entity.SecurityIpAcl;
import io.github.taills.jpa.converter.InetAddressConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SecurityIpAclServiceTest {

    @Autowired
    private SecurityIpAclService securityIpAclService;

    @Test
    @Transactional
    @Rollback
    public void test() {
        SecurityIpAcl acl = new SecurityIpAcl();
        acl.setAclType("DENY");
        acl.setDescription("test");
        acl.setIpRangeBegin("10.136.28.100");
        acl.setIpRangeEnd("10.136.28.200");
        securityIpAclService.save(acl);

        acl = new SecurityIpAcl();
        acl.setAclType("ALLOW");
        acl.setDescription("test");
        acl.setIpRangeBegin("1050:0:0:0:5:600:300c:326b");
        acl.setIpRangeEnd("1050:0:0:0:5:600:300c:336b");
        securityIpAclService.save(acl);


        acl = new SecurityIpAcl();
        acl.setAclType("ALLOW");
        acl.setDescription("test");
        acl.setIpRangeBegin("192.168.0.1");
        acl.setIpRangeEnd("192.168.1.254");
        securityIpAclService.save(acl);


        List<SecurityIpAcl> list = securityIpAclService.findAll();
        for (int i = 0; i < list.size(); i++) {
            SecurityIpAcl securityIpAcl = list.get(i);
            log.info("{} {}", i, securityIpAcl);
        }

        Assert.assertTrue("10.136.28.105 应该在 deny 列表中",securityIpAclService.inDenyList("10.136.28.105"));

        Assert.assertTrue("1050:0:0:0:5:600:300c:3280 应该在 allow 列表中",securityIpAclService.inAllowList("1050:0:0:0:5:600:300c:3280"));

        Assert.assertTrue("192.168.1.100 应该在 allow 列表中",securityIpAclService.inAllowList("192.168.1.100"));



    }
}