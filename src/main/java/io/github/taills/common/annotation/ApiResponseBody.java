package io.github.taills.common.annotation;


import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author taills
 * @date 2019/05/01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@RestController
public @interface ApiResponseBody {

}
