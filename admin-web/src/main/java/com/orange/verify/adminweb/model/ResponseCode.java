package com.orange.verify.adminweb.model;

public enum ResponseCode {

    SUCCESS(10,"操作成功"),
    QUERY_SUCCESS(9,"查询成功"),
    ERROR(11,"操作失败"),

    UNKNOWN_ERROR(901,"不好意思，发生了未知错误"),

    PARAMETER_ERROR(902,"参数错误"),

    EMPTY(17,"无"),

    PRODUCTION_TOO_MUCH(15,"生产过多充值卡密"),

    REGISTER_SUCCESS(15,"注册成功"),
    REGISTER_ERROR(15,"注册失败"),
    LOGIN_SUCCESS(15,"登陆成功"),
    LOGIN_ERROR(15,"登陆失败"),

    SOFT_CLOSE(15,"软件关闭开放使用"),

    ACCOUNT_BLACKLIST(15,"此账号已被加入黑名单"),

    SOFT_NO_CHANGE(15,"软件不支持换绑机器"),

    BINDING_CARD_SUCCESS(15,"卡密绑定成功"),

    BINDING_CODE_SUCCESS(15,"换绑定成功"),

    REGISTER_CLOSE(15,"注册关闭"),

    TOO_FAST(15,"访问过快"),

    VERSIONS_EMPTY(15,"版本不存在"),

    KEY_EMPTY(15,"钥匙为空"),

    ACCOUNT_EMPTY(15,"用户不存在"),

    CARD_EMPTY(15,"卡密不存在"),

    CARD_USE(15,"卡密已被使用"),

    CARD_CLOSURE(15,"卡密已被封停"),

    CARD_PAST_DUE(15,"卡密已过期"),

    KEY_ERROR(15,"服务器钥匙错误"),

    ACCOUNT_ALREADY_EXIST(15,"用户名已存在"),

    PASSWORD_LENGTH_ERROR(15,"密码长度最多只能10位哟！"),

    SOFT_EMPTY(15,"软件不存在"),

    BAIDU_API_ERROR(15,"IP错误"),

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
