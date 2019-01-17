package com.orange.verify.api.sc;

/**
 * Soft
 * 服务状态 0.收费 1.免费开放 2.关闭开放使用
 */
public enum SoftServiceStatus {

    CHARGE(0,"收费"),
    FREE(1,"免费开放"),
    CLOSE(2,"关闭开放使用")
    ;

    private int statusCode;

    private String content;

    SoftServiceStatus(int statusCode,String content) {
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
