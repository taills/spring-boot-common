package com.gxmafeng.config;

import lombok.Data;

/**
 * @ClassName SwaggerContact
 * @Description
 * @Author nil
 * @Date 2021/10/21 10:30 下午
 **/
@Data
public class SwaggerContact {
    private String name;
    private String url;
    private String email;

    public SwaggerContact(String name, String url, String email) {
        this.name = name;
        this.url = url;
        this.email = email;
    }
}
