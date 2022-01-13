package io.github.taills.common.security.jti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @ClassName SimpleJtiServiceConfiguration
 * @Description
 * @Author taills
 * @Date 2022/1/12 5:19 PM
 **/
@Configuration
public class SimpleJtiServiceConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public JtiService jtiService() {
        Map<String, JtiService> beans = applicationContext.getBeansOfType(JtiService.class);
        // 判断是否已经有 JtiService 的实例，没有再创建 SimpleJtiService
        if (beans.size() == 0) {
            return new SimpleJtiService();
        } else {
            return (JtiService) beans.values().toArray()[0];
        }
    }
}
