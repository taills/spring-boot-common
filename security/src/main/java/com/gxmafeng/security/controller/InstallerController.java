package com.gxmafeng.security.controller;

import com.gxmafeng.annotation.ApiResponseBody;
import com.gxmafeng.security.installer.InitDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AuthenticationController
 * @Description
 * @Author nil
 * @Date 2021/10/22 10:47 下午
 **/
@RestController
@ApiResponseBody
@RequestMapping("/installer")
@Slf4j
public class InstallerController {

    @Autowired
    private InitDatabase initDatabase;

    @GetMapping("/init")
    public void init() {
        initDatabase.init();
    }

}
