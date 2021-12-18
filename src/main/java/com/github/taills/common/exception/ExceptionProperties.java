package com.github.taills.common.exception;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @ClassName ExceptionProperties
 * @Description
 * @Author nil
 * @Date 2021/12/17 3:59 PM
 **/
@Configuration
@Slf4j
public class ExceptionProperties {

    private Map<Integer, String> error = new HashMap<>();

    @PostConstruct
    void init() throws IOException {
        EncodedResource resource = new EncodedResource(new ClassPathResource("/exception.properties"), Charsets.UTF_8);
        log.info("异常代码配置路径 {} ", resource.getResource().getFile().getAbsolutePath());
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
        properties.keySet().forEach(key -> error.put(Integer.parseInt(key.toString()), properties.getProperty((String) key)));
        log.info("加载异常代码：{}", error);
    }

    public String msg(Integer code) {
        log.debug("获取异常代码对应的描述信息 {}", code);
        return this.error.get(code) != null ? this.error.get(code) : "未知异常: " + code;
    }

}
