package io.github.taills.common.security.filter;

import io.github.taills.common.response.ApiResult;
import io.github.taills.common.response.ApiResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author taills
 * Create On 2020/6/4 10:00 下午
 */
@Component
@Slf4j
public class JwtAuthenticationEntrance implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 5301523403478859785L;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.debug("JwtAuthenticationEntryPoint Unauthorized");
        response.setHeader("Content-Type","application/json");
        OutputStream out = response.getOutputStream();
        ApiResult<String> result = ApiResult.failure(ApiResultStatus.UNAUTHORIZED);
        out.write(result.toJsonString().getBytes());
        out.flush();
    }
}
