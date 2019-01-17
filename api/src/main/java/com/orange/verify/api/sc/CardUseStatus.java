package com.orange.verify.api.sc;

/**
 * Card
 * 使用状态 0.未使用 1.已使用
 */
public enum CardUseStatus {

    YES(1,"已使用"),
    NO(0,"未使用")
    ;

    private int statusCode;

    private String content;

    CardUseStatus(int statusCode,String content) {
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
