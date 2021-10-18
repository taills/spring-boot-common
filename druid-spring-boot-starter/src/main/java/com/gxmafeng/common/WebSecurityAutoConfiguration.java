package com.gxmafeng.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName WebSecurityAutoConfiguration
 * @Description
 * @Author nil
 * @Date 2021/10/18 5:18 下午
 **/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("禁用 csrf");
        httpSecurity.authorizeRequests().anyRequest().authenticated()
//                .and().oauth2Login()
                .and().formLogin()
                .and().csrf().disable();
    }
}
