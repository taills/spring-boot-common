package io.github.taills.common.jpa.repository;

import io.github.taills.common.jpa.entity.SecurityIpAcl;
import io.github.taills.common.jpa.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * SecurityIpAcl Repository层
 *
 * @author automatically generated by taills's tool
 * @date 2022-01-17 20:16:59
 */
public interface SecurityIpAclRepository extends BaseRepository<SecurityIpAcl, Integer>, SecurityIpAclRepositoryCustom {

    /**
     * 判断给定IP是否在 allow list 中
     * @param ip
     * @return
     */
    @Query(nativeQuery = true,value = "select count(id) from security_ip_acl" +
            " where ip_range_begin <= inet6_aton(:ip)" +
            " and ip_range_end >= inet6_aton(:ip)" +
            " and acl_type = 'ALLOW'" +
            " and is_deleted = false")
    Integer countInAllowList(@Param("ip") String ip);

    /**
     * 判断给定IP是否在 deny list 中
     * @param ip
     * @return
     */
    @Query(nativeQuery = true,value = "select count(id) from security_ip_acl" +
            " where ip_range_begin <= inet6_aton(:ip)" +
            " and ip_range_end >= inet6_aton(:ip)" +
            " and acl_type = 'DENY'" +
            " and is_deleted = false")
    Integer countInDenyList(@Param("ip") String ip);
}