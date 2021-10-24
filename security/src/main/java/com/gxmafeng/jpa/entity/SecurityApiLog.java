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
 * API日志
 *
 * @author auto generated
 * @date 2021-10-24 13:56:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "security_api_log")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update security_api_log set is_deleted = true where id = ?")
@ApiModel(value = "API日志")
public class SecurityApiLog extends BaseEntity implements Serializable {

	/**
	 * URL
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "URL")
	@Column(name = "url", nullable = true, length = 1024)
	private String url;

	/**
	 * HTTP Method
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "HTTP Method")
	@Column(name = "http_method", nullable = true, length = 10)
	private String httpMethod;

	/**
	 * Java Class Method
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "Java Class Method")
	@Column(name = "class_method", nullable = true, length = 128)
	private String classMethod;

	/**
	 * ip
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "ip")
	@Column(name = "ip", nullable = true, length = 64)
	private String ip;

	/**
	 * request args
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "request args")
	@Column(name = "request_args", nullable = true, length = 1024)
	private String requestArgs;

	/**
	 * response args
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "response args")
	@Column(name = "response_args", nullable = true, length = 1024)
	private String responseArgs;

	/**
	 * time-consuming(ms)
	 * nullable : true
	 * default  : 0
	 */
	@ApiModelProperty(value = "time-consuming(ms)")
	@Column(name = "time_consuming", nullable = true, length = 10)
	private Integer timeConsuming;

	/**
	 * 用户ID
	 * nullable : false
	 * default  : null
	 */
	@ApiModelProperty(value = "用户ID")
	@Column(name = "user_id", nullable = true, length = 20)
	private String userId;
}
