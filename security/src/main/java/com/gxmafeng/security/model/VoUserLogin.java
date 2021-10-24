package com.gxmafeng.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName UserLogin
 * @Description
 * @Author nil
 * @Date 2021/10/22 10:50 下午
 **/
@ApiModel(value = "用户登录信息，使用用户名或密码进行登录")
public class VoUserLogin implements Serializable {
    private static final long serialVersionUID = -817442657357638827L;
    @ApiModelProperty(value = "用户名")
    private String username = "";

    @ApiModelProperty(value = "手机号")
    private String mobile = "";

    @ApiModelProperty("密码")
    private String password = "";

    @Override
    public String toString() {
        return "UserLogin{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
