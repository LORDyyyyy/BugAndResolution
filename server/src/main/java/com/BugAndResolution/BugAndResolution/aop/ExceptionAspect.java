package com.BugAndResolution.BugAndResolution.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionAspect {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAspect.class);

    @Pointcut("execution(* com.BugAndResolution.BugAndResolution.service..*.*(..))")
    public void serviceMethods() {}

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logExceptions(JoinPoint joinPoint, Exception ex) {
        log.error("### Exception in Method ###");
        log.error("Method: {}", joinPoint.getSignature());
        log.error("Exception: {}", ex.getMessage());
    }

}
