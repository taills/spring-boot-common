package io.github.taills.common.jpa.repository;

import io.github.taills.common.jpa.entity.SecurityRole;
;

/**
 * SecurityRole Repository层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
public interface SecurityRoleRepository extends BaseRepository<SecurityRole, String> {
    /**
     * 统计 roleName，只有0 或 1　
     * @param roleName
     * @return
     */
    long countAllByRoleName(String roleName);
}
