package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {
    // setup logger
    private Logger myLogger =
            Logger.getLogger(getClass().getName());
    //setup pointcut declarations
    @Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
    private void forControllerPackage(){}

    @Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
    private void forServicePackage(){}

    @Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
    private void forDaoPackage(){}

    @Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
    private void forAppFlow(){}

    // add @Before advice
    @Before("forAppFlow()")
    private void before(JoinPoint joinPoint){
        //display method that is called
        String method = joinPoint.getSignature().toShortString();
        myLogger.info(">> @Before: calling method: " + method);
        // get method's arguments
        Object[] args = joinPoint.getArgs();
        // display method's args if there are any
        if(args.length !=0 ){
            myLogger.info(">> @Before: method arguments: ");
            for(Object o : args) myLogger.info(">>"+o.toString());
        }
    }

    // add @AfterReturning Advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result")
    private void afterReturning(JoinPoint joinPoint, Object result){
        // display method we are returning from
        String method = joinPoint.getSignature().toShortString();
        myLogger.info(">> @AfterReturning: calling method: " + method);

        // display data returned
        if(result != null)
            myLogger.info(">> @AfterReturning: method return data: " + result);
    }
}
