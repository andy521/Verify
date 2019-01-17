package com.orange.verify.api.sc;

/**
 * Soft
 * 软件被留言 是否邮件通知 0.通知 1.不通知
 */
public enum SoftEmailNotificatio {

    YES(0,"通知"),
    NO(1,"不通知")
    ;

    private int statusCode;

    private String content;

    SoftEmailNotificatio(int statusCode,String content) {
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
