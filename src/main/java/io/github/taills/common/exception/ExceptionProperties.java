package io.github.taills.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @ClassName ExceptionProperties
 * @Description
 * @Author nil
 * @Date 2021/12/17 3:59 PM
 **/
@Component
@Slf4j
public class ExceptionProperties {

    private Map<Integer, String> error = new HashMap<>();

    @PostConstruct
    void init() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/exception*.properties");
        for (Resource resource : resources) {
            try (InputStreamReader isr = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                Properties properties = new Properties();
                properties.load(isr);
                properties.keySet().forEach(key -> error.put(Integer.parseInt(key.toString()), properties.getProperty((String) key)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("加载异常代码：{}", error);
    }

    public String msg(Integer code) {
        return this.error.get(code) != null ? this.error.get(code) : "未知异常: " + code;
    }

}
