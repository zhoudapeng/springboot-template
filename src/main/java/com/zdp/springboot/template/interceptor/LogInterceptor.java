package com.zdp.springboot.template.interceptor;

import com.google.gson.Gson;
import com.zdp.springboot.template.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/4
 * Time 下午1:25
 */
@Aspect
@Configuration
@Order(1)
public class LogInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    @Pointcut("execution(* com.zdp.springboot.template.controller..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        Map<String,String[]> params = servletRequestAttributes.getRequest().getParameterMap();
        long startTime = System.currentTimeMillis();
        logger.info("receive request,params = " + new Gson().toJson(params));
        Object result = pjp.proceed();
        logger.info("invoke finished,response = " + JsonUtil.toJson(result));
        long now = System.currentTimeMillis();
        logger.info("invoke cost time:" + (now - startTime) + "ms");
        return result;
    }
}
