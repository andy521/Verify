package com.orange.verify.api.sc;

import java.io.Serializable;

/**
 * InterfaceManagement
 * ipHandle 1开启ip限流控制 0关闭
 */
public enum InterfaceManagementIpHandle implements Serializable {

    CLOSE(0,"关闭"),
    START(1,"开启")
    ;

    private int statusCode;

    private String content;

    InterfaceManagementIpHandle(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }

}
