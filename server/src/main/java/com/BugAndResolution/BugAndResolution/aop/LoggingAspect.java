package com.BugAndResolution.BugAndResolution.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;


@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);



    @Pointcut("execution(* com.BugAndResolution.BugAndResolution.service..*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeServicesMethods(JoinPoint joinPoint){
        log.info("Before The Method Execution");
        log.info("Execution Method: {}",joinPoint.getSignature());
        Object[] args=joinPoint.getArgs();
        if(args.length>0){
            for(Object arg:args) {
                log.info("Input Argument : {}", arg);
            }
        }
        else {
            log.info("No Input Arguments");
        }
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterServiceMethods(JoinPoint joinPoint, Object result) {
        log.info("### After Method Execution ###");
        log.info("Executed method: {}", joinPoint.getSignature());
        log.info("Returned value: {}", result);
    }



    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

}
