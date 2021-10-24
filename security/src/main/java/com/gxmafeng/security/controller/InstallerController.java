package com.gxmafeng.security.controller;

import com.gxmafeng.annotation.ApiResponseBody;
import com.gxmafeng.jpa.entity.SecurityUser;
import com.gxmafeng.jpa.service.SecurityUserService;
import com.gxmafeng.response.ApiResult;
import com.gxmafeng.security.installer.InitDatabase;
import com.gxmafeng.security.model.VoUserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
