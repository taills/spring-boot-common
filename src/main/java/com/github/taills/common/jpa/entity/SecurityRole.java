package com.github.taills.common.jpa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;


/**
 * 角色
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_role")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_role set is_deleted = true where id = ?")
@ApiModel(value = "角色")
public class SecurityRole extends BaseEntity implements Serializable {

	/**
	 * 角色名称
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "角色名称")
	@Column(name = "role_name", nullable = true, length = 64)
	private String roleName;

	/**
	 * 描述
	 * nullable : true
	 * default  : null
	 */
	@ApiModelProperty(value = "描述")
	@Column(name = "description", nullable = true, length = 128)
	private String description;
}
