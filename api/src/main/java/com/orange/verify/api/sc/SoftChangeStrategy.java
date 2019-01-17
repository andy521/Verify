package com.orange.verify.api.sc;

/**
 * Soft
 * 换绑策略 0.支持换绑定 1.不支持换绑定
 */
public enum SoftChangeStrategy {

    YES(0,"支持换绑定"),
    NO(1,"不支持换绑定")
    ;

    private int statusCode;

    private String content;

    SoftChangeStrategy(int statusCode,String content) {
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
