package com.zdp.springboot.template.controller;

import com.zdp.springboot.template.annotation.LoggedIn;
import com.zdp.springboot.template.entity.common.User;
import com.zdp.springboot.template.service.LoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/19
 * Time 下午2:53
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Resource
    private LoginService loginService;

    @RequestMapping("")
    public Object logout(@LoggedIn User user) {
        loginService.logout(user);
        return null;
    }
}
