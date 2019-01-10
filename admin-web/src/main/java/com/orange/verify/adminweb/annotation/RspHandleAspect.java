package com.orange.verify.adminweb.annotation;

import com.orange.verify.adminweb.model.Response;
import com.orange.verify.adminweb.model.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * rsp简化aop
 * @author Orange
 * @date 2018/10/13
 */
@Aspect
@Component
@Slf4j
public class RspHandleAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.orange.verify.adminweb.annotation.RspHandle)")
    public void annotationPointCut() {

    }

    @Around("annotationPointCut()")
    public Response arround(ProceedingJoinPoint pjp) throws Throwable {

        RspHandle rspHandle = getRspHandle(pjp);

        boolean setErrorInfo = rspHandle.isSetErrorInfo();
        boolean ipHandle = rspHandle.ipHandle();
        long ipVisits = rspHandle.ipVisits();
        long ipRedisInterval = rspHandle.ipRedisInterval();

        if (ipHandle == true) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            String prefix =
                    pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + ".";
            String remoteAddr = request.getRemoteAddr();

            String key = prefix + remoteAddr;
            key = key.replaceAll(":",".");

            long count = redisTemplate.opsForValue().increment(key, 1);
            if (count == 1) {
                redisTemplate.expire(key, ipRedisInterval, TimeUnit.MINUTES);
            }
            if (count > ipVisits) {
                Long expire = redisTemplate.getExpire(key);
                String msg = "访问过快哦！" + expire + "秒过后才能继续访问";
                return Response.build(ResponseCode.TOO_FAST,msg);
            }
        }

        try {

            long startTime = System.currentTimeMillis();

            Response response = (Response) pjp.proceed();

            long endTime = System.currentTimeMillis();

            response.setTotalTime(endTime - startTime);

            return response;

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

    }

    private RspHandle getRspHandle(ProceedingJoinPoint pjp) {
        MethodSignature sign =  (MethodSignature) pjp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(RspHandle.class);
    }

}
