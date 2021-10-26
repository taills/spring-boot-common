package com.gxmafeng.response;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.ToString;
/**
 * @author taills
 * @date 2019/05/01
 */
@Getter
@ToString
public class ApiResult<T> {
    /**
     * 业务错误码
     */
    private Integer code;
    /**
     * 信息描述
     */
    private String message;
    /**
     * 返回参数
     */
    private T data;

    private final static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

    private ApiResult(ApiResultStatus apiResultStatus, T data) {
        this.code = apiResultStatus.getCode();
        this.message = apiResultStatus.getMessage();
        this.data = data;
    }

    private ApiResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 业务成功，无参
     * @return
     */
    public static ApiResult<Void> success() {
        return new ApiResult<Void>(ApiResultStatus.SUCCESS, null);
    }

    /**
     * 业务成功，返回正常业务码及 data
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(ApiResultStatus.SUCCESS, data);
    }

    /**
     * 业务成功，使用预设的 ApiResultStatus 外加 data
     * @param apiResultStatus
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> success(ApiResultStatus apiResultStatus, T data) {
        if (apiResultStatus == null) {
            return success(data);
        }
        return new ApiResult<T>(apiResultStatus, data);
    }

    /**
     * 业务异常，无参数
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failure() {
        return new ApiResult<T>(ApiResultStatus.INTERNAL_SERVER_ERROR, null);
    }

    /**
     * 业务异常，使用预设的 ApiResultStatus
     * @param apiResultStatus
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failure(ApiResultStatus apiResultStatus) {
        return failure(apiResultStatus, null);
    }

    /**
     * 业务异常，使用预设的 ApiResultStatus 及附加 data
     * @param apiResultStatus
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failure(ApiResultStatus apiResultStatus, T data) {
        if (apiResultStatus == null) {
            return new ApiResult<T>(ApiResultStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new ApiResult<T>(apiResultStatus, data);
    }

    /**
     * 业务异常，使用自定义的 code、message、data
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResult<T> failure(Integer code, String message, T data) {
        return new ApiResult<>(code, message, data);
    }

    public static <T> ApiResult<T> failure(Integer code, String message) {
        return new ApiResult<>(code, message, null);
    }

    public String toJsonString() {
        return GSON.toJson(this);
    }
}
