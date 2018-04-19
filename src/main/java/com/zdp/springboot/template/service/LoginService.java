package com.zdp.springboot.template.service;

import com.zdp.springboot.template.entity.common.User;
import com.zdp.springboot.template.entity.login.LoginRequest;

public interface LoginService {
    User login(LoginRequest request);

    void logout(User user);
}
