package com.gxmafeng.jpa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.gxmafeng.jpa.entity.BaseEntity;
import javax.persistence.*;

import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户组
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
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
	private Long parentId;

	/**
	 * 名称
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "名称")
	@Column(name = "group_name", nullable = true, length = 64)
	private String groupName;

	@OneToMany(mappedBy = "parentId",fetch = FetchType.EAGER)
	private List<SecurityGroup> childrenGroups = new ArrayList<>();


	@ApiModelProperty(value = "角色列表")
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "security_group_role",
			joinColumns = @JoinColumn(name="group_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<SecurityRole> roles = new ArrayList<>();
}
