package com.zdp.springboot.template.interceptor;

import com.zdp.springboot.template.annotation.NoNeedLogin;
import com.zdp.springboot.template.consts.ParamConsts;
import com.zdp.springboot.template.entity.common.User;
import com.zdp.springboot.template.enumeration.ResultCode;
import com.zdp.springboot.template.exception.BusinessException;
import com.zdp.springboot.template.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/4
 * Time 上午12:03
 */
@Aspect
@Configuration
@Order(3)
public class LoginCheckInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

    @Pointcut("execution(* com.zdp.springboot.template.controller..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void loginCheckPointcut(){}

    @Around("loginCheckPointcut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Annotation annotationOfMethod = signature.getMethod().getAnnotation(NoNeedLogin.class);
        Annotation annotationOfClass = pjp.getTarget().getClass().getAnnotation(NoNeedLogin.class);
        if (annotationOfMethod != null || annotationOfClass != null) {
            logger.info("controller方法或者类上有NoNeedLogin注解，无需检测登录");
            return pjp.proceed();
        }
        boolean hasLogin = hasLogin();
        logger.info("hasLogin=" + hasLogin);
        if (!hasLogin) {
            Exception e = new BusinessException(ResultCode.NON_LOGIN);
            throw e;
        }
        return pjp.proceed();
    }

    private boolean hasLogin() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        User user = (User) request.getAttribute(ParamConsts.USER);
        logger.info("loggedin user=" + JsonUtil.toJson(user));
        return user != null;
    }
}
