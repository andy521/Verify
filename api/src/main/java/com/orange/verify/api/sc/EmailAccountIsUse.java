package com.orange.verify.api.sc;

/**
 * EmailAccount
 * 是否使用 0.使用中 1.未使用
 */
public enum  EmailAccountIsUse {

    YES(0,"使用中"),
    NO(1,"未使用")
    ;

    private int statusCode;

    private String content;

    EmailAccountIsUse(int statusCode,String content) {
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
