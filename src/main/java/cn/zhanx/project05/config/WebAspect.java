package cn.zhanx.project05.config;


import cn.zhanx.project05.utils.IpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Order(1)
@Aspect
@Component
@EnableAspectJAutoProxy
@Slf4j
public class WebAspect {

    @Pointcut("execution(  * cn.zhanx.project05.controller.*.*(..))")
    public void shopControllerPointcut() {
    }

    @Around("shopControllerPointcut()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        Object body = null;
        Date reqTime = new Date();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = point.getArgs();
        String reqIp = IpUtil.getClientIP(request);
        try {
            log.info("*****************************************");
            log.info("URL : {}", request.getRequestURL().toString());
            log.info("METHOD : {}", point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
            log.info("IP : {}, TYPE : {}", reqIp, request.getMethod());
            log.info("ARGS : {}", Arrays.toString(args).length() < 500 ? Arrays.toString(args) : Arrays.toString(args).substring(0, 500) + "...");
            log.info("TOKEN:{}", request.getHeader("token"));
            log.info("User-Agent:" + request.getHeader("User-Agent"));

            body = point.proceed();

            log.info("RESULT : {}", JSON.toJSONString(body).length() < 500 ? JSON.toJSONString(body) : JSON.toJSONString(body).substring(0, 500) + "...");
        } catch (RuntimeException e) {
            body = e;
            log.info("EXCEPTION : {}", e.getMessage());
            throw e;
        } finally {
            try {
                //asyncAddWebLog()
                log.info("*****************************************");
            } catch (Exception e) {
                log.error("WebAspect asyncAddWebLog Exception:{}", e);
            }
        }
        return body;
    }

}
