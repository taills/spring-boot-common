package com.gxmafeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;
/**
 * @author taills
 * @date 2021/10/26
 */
@SpringBootApplication
@EnableOpenApi
@EnableCaching
public class SecurityApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class, args);
    }

}
