package com.orange.verify.api.sc;

/**
 * SoftVersions
 * 是否强制更新 0.不强制 1.强制
 */
public enum SoftVersionsNovatioNecessaria {

    YES(1,"强制"),
    NO(0,"不强制")
    ;

    private int statusCode;

    private String content;

    SoftVersionsNovatioNecessaria(int statusCode,String content) {
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
