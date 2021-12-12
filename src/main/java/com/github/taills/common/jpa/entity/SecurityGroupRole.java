package com.github.taills.common.jpa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.*;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户组-角色 [中间表]
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_group_role")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_group_role set is_deleted = true where id = ?")
@ApiModel(value = "用户组-角色 [中间表]")
@Slf4j
public class SecurityGroupRole extends BaseEntity implements Serializable {

    /**
     * 用户组ID
     * nullable : false
     * default  : null
     */
    @ApiModelProperty(value = "用户组ID")
    @Column(name = "group_id", nullable = true, length = 20)
    private String groupId;

    /**
     * 角色ID
     * nullable : false
     * default  : null
     */
    @ApiModelProperty(value = "角色ID")
    @Column(name = "role_id", nullable = true, length = 20)
    private String roleId;

    @PrePersist
    void dddddd() {
        log.debug("准备插入: {}", this);
    }
}
