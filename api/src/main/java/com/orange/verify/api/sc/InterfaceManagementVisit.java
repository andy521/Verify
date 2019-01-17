package com.orange.verify.api.sc;

import java.io.Serializable;

/**
 * InterfaceManagement
 * visit 1=可以访问 0=接口关闭
 */
public enum InterfaceManagementVisit implements Serializable {

    CLOSE(0,"拒绝访问"),
    OPEN(1,"可以访问")
    ;

    private int statusCode;

    private String content;

    InterfaceManagementVisit(int statusCode,String content) {
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
