package com.zdp.springboot.template.service;

import com.zdp.springboot.template.entity.common.User;

public interface UserResolveService {
    User resolve(String accessToken);
}
