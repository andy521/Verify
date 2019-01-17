package com.orange.verify.api.sc;

/**
 * Card
 * 销售状态 0.未卖出 1.已卖出
 */
public enum CardSellStatus {

    YES(1,"已卖出"),
    NO(0,"未卖出")
    ;

    private int statusCode;

    private String content;

    CardSellStatus(int statusCode,String content) {
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
