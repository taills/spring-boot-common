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
 * 用户-用户组 [中间表]
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_user_group")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_user_group set is_deleted = true where id = ?")
@ApiModel(value = "用户-用户组 [中间表]")
public class SecurityUserGroup extends BaseEntity implements Serializable {

	/**
	 * 用户ID
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "用户ID")
	@Column(name = "user_id", nullable = true, length = 20)
	private String userId;

	/**
	 * 用户组ID
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "用户组ID")
	@Column(name = "group_id", nullable = true, length = 20)
	private String groupId;
}
