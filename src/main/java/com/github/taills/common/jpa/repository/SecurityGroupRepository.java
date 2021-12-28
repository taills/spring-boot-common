package com.github.taills.common.jpa.repository;

import com.github.taills.common.jpa.entity.SecurityGroup;
;

/**
 * SecurityGroup Repository层
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
public interface SecurityGroupRepository extends BaseRepository<SecurityGroup, String> {

    /**
     * 根据 groupName 统计条数，正常情况下只会有 1 和 0
     * @param groupName
     * @return
     */
    long countAllByGroupName(String groupName);
}
