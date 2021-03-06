package io.github.taills.common.jpa.repository;

import io.github.taills.common.jpa.entity.SecurityUser;
;

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

}
