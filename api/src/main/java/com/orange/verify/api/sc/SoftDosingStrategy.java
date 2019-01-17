package com.orange.verify.api.sc;

/**
 * Soft
 * 多开策略 0.只支持单机 1.无限制
 */
public enum SoftDosingStrategy {

    SINGLE(0,"只支持单机"),
    UNLIMITED(1,"无限制")
    ;

    private int statusCode;

    private String content;

    SoftDosingStrategy(int statusCode,String content) {
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
