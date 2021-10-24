package com.gxmafeng.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public enum ApiResultStatus {

    SUCCESS(HttpStatus.OK, 200, "成功"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,405,"暂不支持该请求方法"),
    FORBIDDEN(HttpStatus.FORBIDDEN,403,"拒绝访问"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,401,"未授权访问"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "错误的请求"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "内部异常");

    /** 返回的HTTP状态码,  符合http请求 */
    private HttpStatus httpStatus;
    /** 业务异常码 */
    private Integer code;
    /** 业务异常信息描述 */
    private String message;

    ApiResultStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
