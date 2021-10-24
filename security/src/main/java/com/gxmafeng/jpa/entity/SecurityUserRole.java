package com.gxmafeng.jpa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.gxmafeng.jpa.entity.BaseEntity;
import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;


/**
 * 用户-角色 [中间表]
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_user_role")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_user_role set is_deleted = true where id = ?")
@ApiModel(value = "用户-角色 [中间表]")
public class SecurityUserRole extends BaseEntity implements Serializable {

	/**
	 * 用户ID
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "用户ID")
	@Column(name = "user_id", nullable = true, length = 20)
	private String userId;

	/**
	 * 角色ID
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "角色ID")
	@Column(name = "role_id", nullable = true, length = 20)
	private String roleId;
}
