package com.zdp.springboot.template.controller;

import com.zdp.springboot.template.annotation.LoggedIn;
import com.zdp.springboot.template.entity.common.User;
import com.zdp.springboot.template.service.IndexService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/9
 * Time 下午5:37
 */
@RestController
@RequestMapping("/index")
public class IndexController {
    @Resource
    private IndexService indexService;

    @RequestMapping("")
    public Object home(@LoggedIn User user) {
        return indexService.assemble(user);
    }
}
