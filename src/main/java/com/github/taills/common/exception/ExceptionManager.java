package com.github.taills.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName ExceptionManager
 * @Description
 * @Author nil
 * @Date 2021/12/17 3:35 PM
 **/
@Component
public class ExceptionManager {

    private static ExceptionProperties exceptionProperties;

    public static ExceptionManager Instance;

    @Autowired
    public ExceptionManager(ExceptionProperties exceptionProperties) {
        this.exceptionProperties = exceptionProperties;
        Instance = this;
    }

    private BaseException createWithCode(Integer code) {
        return new BaseException(code, exceptionProperties.msg(code));
    }

    private BaseException createWithCodeAndMessage(Integer code, String msg) {
        return new BaseException(code, String.format("%s，附加信息：%s", exceptionProperties.msg(code), msg));
    }

    /**
     * 根据异常代码抛出异常
     *
     * @param code 异常代码
     * @return 异常附加信息
     */
    public static BaseException create(Integer code) {
        return Instance.createWithCode(code);
    }

    /**
     * 根据异常代码 + 附加信息 抛出异常
     *
     * @param code 异常代码
     * @param msg  异常附加信息
     * @return
     */
    public static BaseException create(Integer code, String msg) {
        return Instance.createWithCodeAndMessage(code, msg);
    }
}
