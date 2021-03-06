package io.github.taills.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName SwaggerProperties
 * @Description
 * @Author nil
 * @Date 2021/10/21 9:51 下午
 **/
@Component
@ConfigurationProperties("swagger")
@Data
public class SwaggerProperties {
    /**
     * 是否开启swagger，生产环境一般关闭，所以这里定义一个变量
     */
    private Boolean enable = false;

    /**
     * 项目应用名
     */
    private String applicationName = "App";

    /**
     * 项目版本信息
     */
    private String applicationVersion = "App";

    /**
     * 项目描述信息
     */
    private String applicationDescription = "";

    /**
     * 分组名称
     */
    private String groupName = "API";

    /**
     * 联系人
     */
    private SwaggerContact contact = new SwaggerContact("Taills", "https://github.com/taills/", "taills@qq.com");

}