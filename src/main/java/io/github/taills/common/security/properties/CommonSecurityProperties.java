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
public class CommonSecurityProperties {
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

    /**
     * 使用 x-forwarded-for http 头里的内容来做判断
     * 这个值容易被伪造，但反代环境又很常见
     * 所以在第一层 nginx 中，需要配置如下参数：
     * proxy_set_header X-Forwarded-For $remote_addr;
     * 在第一层 nginx 里把 remote addr 直接赋值给 x-forwarded-for 头
     * 如果没有反代环境，直接启用 ipAclUseRemoteAddr 就行了
     */
    private boolean ipAclUseXForwardedFor = true;

    /**
     * 使用 remote addr 里面的 IP 来做判断
     */
    private boolean ipAclUseRemoteAddr = false;


}
