package com.zdp.springboot.template.controller;

import com.zdp.springboot.template.annotation.NoNeedLogin;
import com.zdp.springboot.template.entity.login.LoginRequest;
import com.zdp.springboot.template.service.LoginService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/3
 * Time 下午2:21
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    private LoginService loginService;

    @RequestMapping("")
    @NoNeedLogin
    public Object login(@Valid LoginRequest request, BindingResult result) {
        return loginService.login(request);
    }
}
