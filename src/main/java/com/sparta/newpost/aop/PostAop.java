package com.sparta.newpost.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PostAop {

    @Pointcut("execution(public * com.sparta.newpost.post.controller..*(..))")
    private void allPostAop(){}

    @Before("allPostAop()")
    public void postNameBefore(JoinPoint joinPoint){
        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "의 AOP 동작");
    }

    @AfterReturning("allPostAop()")
    public void postNameAfterReturning(JoinPoint joinPoint){
        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "의 AOP 동작");
    }

}
