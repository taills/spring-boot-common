package com.gxmafeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class SecurityApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class, args);
    }

}
