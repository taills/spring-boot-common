package com.github.taills.common.exception;

import com.github.taills.common.response.ApiResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName BaseException
 * @Description 业务异常类，继承运行时异常
 * @Author nil
 * @Date 2021/10/19 2:51 下午
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException{

    private ApiResult apiResult;

    public BaseException(ApiResult apiResult) {
        this.apiResult = apiResult;
    }

    public BaseException(Throwable cause, ApiResult apiResult) {
        super(cause);
        this.apiResult = apiResult;
    }
}