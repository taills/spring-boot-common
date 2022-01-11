package io.github.taills.common.jpa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * 用户基础信息
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_user")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_user set is_deleted = true where id = ?")
@ApiModel(value = "用户基础信息")
public class SecurityUser extends BaseEntity implements Serializable {

    /**
     * 用户名
     * nullable : false
     * default  : null
     */
    @ApiModelProperty(value = "用户名")
    @Column(name = "username", nullable = true, length = 64)
    private String username;

    /**
     * 昵称
     * nullable : false
     * default  : null
     */
    @ApiModelProperty(value = "昵称")
    @Column(name = "nickname", nullable = true, length = 64)
    private String nickname;


    /**
     * 密码
     * nullable : false
     * default  : null
     */
    @ApiModelProperty(value = "密码")
    @Column(name = "password", nullable = true, length = 64)
    private String password;

    /**
     * 过期时间
     * nullable : true
     * default  : '2099-12-31 00:00:00'
     */
    @ApiModelProperty(value = "过期时间")
    @Column(name = "expired_at", nullable = true)
    private java.util.Date expiredAt;

    /**
     * 是否锁定
     * nullable : true
     * default  : 0
     */
    @ApiModelProperty(value = "是否锁定")
    @Column(name = "is_locked", nullable = true, length = 1)
    private Boolean isLocked;

    /**
     * 是否启用
     * nullable : true
     * default  : 0
     */
    @ApiModelProperty(value = "是否启用")
    @Column(name = "is_enabled", nullable = true, length = 1)
    private Boolean isEnabled;

    @ApiModelProperty(value = "关联用户的角色列表")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "security_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SecurityRole> roles = new HashSet<>();


    @ApiModelProperty(value = "所属组")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "security_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<SecurityGroup> groups = new HashSet<>();

    public Set<SecurityRole> getRoles() {
        traversingGroupRoles(this.getGroups());
        return this.roles;
    }
    public void traversingGroupRoles(Set<SecurityGroup> root) {
        for (SecurityGroup node : root) {
            this.roles.addAll(node.getRoles());
            Set<SecurityGroup> childres = node.getChildrenGroups();
            if (childres != null && childres.size() > 0) {
                traversingGroupRoles(childres);
            }
        }
    }
}
