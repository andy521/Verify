package com.orange.verify.api.sc;

/**
 * CardType
 * 卡类单位 0.分 1.时 2.天 3.周 4.月 5.年
 */
public enum CardTypeUnit {

    MINUTE(0,"分"),
    HOUR(1,"时"),
    DAY(2,"天"),
    WEEK(3,"周"),
    MONTH(4,"月"),
    YEAR(5,"年")
    ;

    private int statusCode;

    private String content;

    CardTypeUnit(int statusCode,String content) {
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
