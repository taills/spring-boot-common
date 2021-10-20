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
 * 用户基础信息
 *
 * @author auto generated
 * @date 2021-10-20 15:44:55
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
	 * 手机号
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "手机号")
	@Column(name = "mobile", nullable = true, length = 12)
	private String mobile;

	/**
	 * 头像 URL
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "头像 URL")
	@Column(name = "avatar_url", nullable = true, length = 256)
	private String avatarUrl;

	/**
	 * 密码
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "密码")
	@Column(name = "password", nullable = true, length = 64)
	private String password;
}
