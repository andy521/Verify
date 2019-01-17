package com.orange.verify.api.sc;

/**
 * Card
 * 是否封停使用 0.未封停 1.已封停
 */
public enum CardClosure {

    YES(1,"已封停"),
    NO(0,"未封停")
    ;

    private int statusCode;

    private String content;

    CardClosure(int statusCode,String content) {
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
