package io.github.taills.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName JwtProperties
 * @Description
 * @Author nil
 * @Date 2022/1/12 3:48 PM
 **/
@Component
@ConfigurationProperties(prefix = "common.security")
@Data
public class SecurityProperties {
    /**
     * JWT 密钥
     */
    private String key = "这是一个必须要改的值。";
    /**
     * 签发 token 的有效时长，单位：秒
     */
    private Integer lifeTime = 3600;

    /**
     * 是否强制二步认证
     */
    private boolean mandatoryTwoStepAuthentication = false;

    /**
     * 签发者，会显示在app里
     */
    private String twoSetpIssuer = "taills";
}
