package com.zdp.springboot.template.interceptor;

import com.zdp.springboot.template.entity.common.AppResponse;
import com.zdp.springboot.template.entity.common.Bstatus;
import com.zdp.springboot.template.enumeration.ResultCode;
import com.zdp.springboot.template.exception.BusinessException;
import com.zdp.springboot.template.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/3
 * Time 下午2:28
 */
@Aspect
@Configuration
@Order(2)
public class ControllerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    @Pointcut("execution(* com.zdp.springboot.template.controller..*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    @Around("controllerMethodPointcut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        AppResponse response = new AppResponse();
        ResultCode resultCode = ResultCode.SUCCESS;
        Object data = null;
        String msg = "";
        try {
            Object[] args = pjp.getArgs();
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    if (arg != null && arg instanceof BindingResult) {
                        BindingResult result = (BindingResult) arg;
                        if (result.hasErrors()) {
                            List<ObjectError> errorList = result.getAllErrors();
                            for (ObjectError error : errorList) {
                                throw new BusinessException(ResultCode.PARAM_ERR, error.getDefaultMessage());
                            }
                        }
                    }
                }
            }
            data = pjp.proceed();
        }catch (BusinessException be) {
            logger.warn("业务异常",be);
            resultCode = be.getResultCode();
            msg = be.getMsg();
        }catch (Exception e) {
            logger.warn("系统异常",e);
            resultCode = ResultCode.SYSTEM_ERROR;
        }
        if (data instanceof AppResponse) {
            return data;
        }
        response.setBstatus(new Bstatus.Builder().setCode(resultCode.getCode()).setDesc(resultCode.getDesc()).setMsg(msg).build());
        response.setData(data);
        return JsonUtil.toJson(response);
    }
}
