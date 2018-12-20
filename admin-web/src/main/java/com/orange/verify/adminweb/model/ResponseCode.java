package com.orange.verify.adminweb.model;

public enum ResponseCode {

    SUCCESS(10,"操作成功"),
    QUERY_SUCCESS(9,"查询成功"),
    ERROR(11,"操作失败"),

    UNKNOWN_ERROR(901,"不好意思，发生了未知错误"),

    PARAMETER_ERROR(902,"参数错误"),
    PRODUCTION_TOO_MUCH(15,"生产过多充值卡密"),

    NOT_ROLE(13,"无角色"),
    NOT_LOGIN(12,"未登录");

    private int code;
    private String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }

}
