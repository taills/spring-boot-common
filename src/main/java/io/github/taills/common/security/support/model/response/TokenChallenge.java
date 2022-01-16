package io.github.taills.common.security.support.model.response;

import lombok.Data;

/**
 * @ClassName TokenChallenge
 * @Description
 * @Author nil
 * @Date 2022/1/16 6:10 PM
 **/
@Data
public class TokenChallenge {

    private String token;

    private String nextStep;

    private String secret;

    private String totpUrl;
}
