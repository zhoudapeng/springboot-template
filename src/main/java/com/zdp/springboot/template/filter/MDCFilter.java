package com.zdp.springboot.template.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @author <a href="mailto:zhoudapeng8888@126.com">zhoudapeng</a>
 * Date 2018/4/4
 * Time 下午3:17
 */
@Component
@Order(1)
@WebFilter(urlPatterns = "/*", filterName = "mdcFilter")
public class MDCFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(MDCFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init MDCFilter...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("TRACE_ID", UUID.randomUUID().toString());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        logger.info("destroy MDCFilter...");
    }
}
