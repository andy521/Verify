package com.orange.verify.common.ip;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

    /**
     *获取Ip
     *@paramrequest
     *@return
     */
    public static String getIp(HttpServletRequest request){
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor)&&!"unKnown".equalsIgnoreCase(XFor)){
            int index=XFor.indexOf(",");
            if(index!=-1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor=Xip;
        if(StringUtils.isNotEmpty(XFor)&&!"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if(StringUtils.isBlank(XFor)||"unknown".equalsIgnoreCase(XFor)){
            XFor=request.getHeader("Proxy-Client-IP");
        }
        if(StringUtils.isBlank(XFor)||"unknown".equalsIgnoreCase(XFor)){
            XFor=request.getHeader("WL-Proxy-Client-IP");
        }
        if(StringUtils.isBlank(XFor)||"unknown".equalsIgnoreCase(XFor)){
            XFor=request.getHeader("HTTP_CLIENT_IP");
        }
        if(StringUtils.isBlank(XFor)||"unknown".equalsIgnoreCase(XFor)){
            XFor=request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(StringUtils.isBlank(XFor)||"unknown".equalsIgnoreCase(XFor)){
            XFor=request.getRemoteAddr();
        }
        return XFor;
    }

}
