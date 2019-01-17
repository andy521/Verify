package com.orange.verify.api.sc;

/**
 * Soft
 * 注册状态 0.开放注册 1.关闭注册
 */
public enum SoftRegisterStatus {

    OPEN(0,"开放注册"),
    CLOSE(1,"关闭注册")
    ;

    private int statusCode;

    private String content;

    SoftRegisterStatus(int statusCode,String content) {
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
