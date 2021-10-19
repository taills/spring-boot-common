package com.gxmafeng.exception;

import com.gxmafeng.response.ApiResponseBody;
import com.gxmafeng.response.ApiResult;
import com.gxmafeng.response.ApiResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ExceptionHandlerAdvice
 * @Description
 * @Author nil
 * @Date 2021/10/19 2:51 下午
 **/
@ControllerAdvice(annotations = ApiResponseBody.class)
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {
    /**
     * 处理未捕获的Exception
     *
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResult.failure(ApiResultStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理未捕获的RuntimeException
     *
     * @param e 运行时异常
     * @return 统一响应体
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResult handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ApiResult.failure(ApiResultStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理业务异常BaseException
     *
     * @param e 业务异常
     * @return 统一响应体
     */
    @ExceptionHandler(BaseException.class)
    public ApiResult handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return e.getApiResult();
    }
}