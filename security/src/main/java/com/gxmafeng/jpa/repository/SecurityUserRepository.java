package com.gxmafeng.jpa.repository;

import com.gxmafeng.jpa.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * SecurityUser Repository层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
public interface SecurityUserRepository extends BaseRepository<SecurityUser, String> {


    /**
     * 查找用户名为... 的 记录
     *
     * @param username
     * @return
     */
    Optional<SecurityUser> findByUsername(String username);

    /**
     * 查找手机号为... 的 记录
     *
     * @param mobile
     * @return
     */
    Optional<SecurityUser> findByMobile(String mobile);

}
