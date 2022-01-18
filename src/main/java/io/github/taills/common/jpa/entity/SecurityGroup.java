package io.github.taills.common.jpa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * 用户组
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_group")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_group set is_deleted = true where id = ?")
@ApiModel(value = "用户组")
public class SecurityGroup extends BaseEntity implements Serializable {

    /**
     * 父用户组ID
     * nullable : true
     * default  : null
     */
    @ApiModelProperty(value = "父用户组ID")
    @Column(name = "parent_id", nullable = true, length = 20)
    private String parentId;

    /**
     * 名称
     * nullable : false
     * default  : null
     */
    @ApiModelProperty(value = "名称")
    @Column(name = "group_name", nullable = true, length = 64)
    private String groupName;


    @OneToMany(mappedBy = "parentId", fetch = FetchType.EAGER)
    @ApiModelProperty(name = "子组", hidden = true)
    private Set<SecurityGroup> childrenGroups = new HashSet<>();


    @ApiModelProperty(value = "角色列表")
    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "security_group_role",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<SecurityRole> roles = new HashSet<>();
}
