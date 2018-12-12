package com.orange.verify.adminweb.annotation;

import com.orange.verify.adminweb.model.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * rsp简化aop
 * @author Orange
 * @date 2018/10/13
 */
@Aspect
@Component
public class RspSetTimeAspect {

    @Pointcut("@annotation(com.orange.verify.adminweb.annotation.RspSetTime)")
    public void annotationPointCut() {

    }

    @Around("annotationPointCut()")
    public Response arround(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();

        //执行方法体
        Response response = (Response) pjp.proceed();

        long endTime = System.currentTimeMillis();

        response.setTotalTime(endTime - startTime);

        return response;

    }

}
