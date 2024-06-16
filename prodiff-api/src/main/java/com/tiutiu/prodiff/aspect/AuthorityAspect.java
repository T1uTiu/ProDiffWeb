package com.tiutiu.prodiff.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuthorityAspect {
    @Pointcut("@annotation(com.tiutiu.prodiff.annotation.Authority)")
    public void authorityPointcut() {
    }

    @Before("authorityPointcut()")
    public void authorize(JoinPoint joinPoint) {
    }
}
