package com.orange.verify.api.sc;

/**
 * Account
 * 是否加入了黑名单 0.不是 1.是的
 */
public enum AccountBlackList {

    YES(1,"是"),
    NO(0,"不是")
    ;

    private int statusCode;

    private String content;

    AccountBlackList(int statusCode,String content) {
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
