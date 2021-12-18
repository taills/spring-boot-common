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

    @Autowired
    ExceptionProperties exceptionProperties;

    public BaseException create(Integer code) {
        return new BaseException(code, exceptionProperties.msg(code));
    }
}
