package com.orange.verify.adminweb.annotation;

import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * rsp简化aop
 * @author Orange
 * @date 2018/10/13
 */
@Aspect
@Component
@Slf4j
public class RspHandleAspect {

    @Pointcut("@annotation(com.orange.verify.adminweb.annotation.RspHandle)")
    public void annotationPointCut() {

    }

    @Around("annotationPointCut()")
    public Response arround(ProceedingJoinPoint pjp) throws Throwable {

        RspHandle rspHandle = getRspHandle(pjp);

        boolean setErrorInfo = rspHandle.isSetErrorInfo();

        long startTime = System.currentTimeMillis();

        Response response = null;
        try {
            response = (Response) pjp.proceed();
        } catch (ParameterError e) {
            return Response.build(ResponseCode.PARAMETER_ERROR,e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            if (setErrorInfo == true) {
                return Response.build(ResponseCode.UNKNOWN_ERROR,e.getMessage());
            } else {
                return Response.build(ResponseCode.UNKNOWN_ERROR);
            }
        }

        long endTime = System.currentTimeMillis();

        response.setTotalTime(endTime - startTime);

        return response;

    }

    private RspHandle getRspHandle(ProceedingJoinPoint pjp) {
        MethodSignature sign =  (MethodSignature) pjp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(RspHandle.class);
    }

}
