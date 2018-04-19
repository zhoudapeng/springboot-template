package com.zdp.springboot.template.service;

import com.zdp.springboot.template.entity.common.User;
import com.zdp.springboot.template.entity.index.IndexResponse;

public interface IndexService {
    IndexResponse assemble(User user);
}
