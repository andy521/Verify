package com.orange.verify.adminweb.model;

/**
 * 基础的返回0-100
 * 成功类1xx 单位为百
 * 失败类9xx 单位为百
 * @author Orange
 */
public enum ResponseCode {

    //成功...
    REGISTER_SUCCESS(101,"注册成功"),

    LOGIN_SUCCESS(100,"登陆成功"),

    BINDING_CARD_SUCCESS(102,"卡密绑定成功"),

    BINDING_CODE_SUCCESS(103,"换绑定成功"),

    LEAVE_MESSAGE_SEND_SUCCESS(104,"留言发送成功"),

    UPDATE_PASSWORD_SUCCESS(105,"改密成功"),

    LOGOUT_SUCCESS(106,"登出成功"),


    //失败
    UNKNOWN_ERROR(901,"不好意思，发生了未知错误"),

    PARAMETER_ERROR(902,"参数错误"),

    EMPTY(903,"无"),

    PRODUCTION_TOO_MUCH(904,"生产过多充值卡密"),

    REGISTER_ERROR(905,"注册失败"),

    LOGIN_ERROR(906,"登陆失败,可能由于密码错误等原因！"),

    SOFT_CLOSE(907,"软件关闭开放使用"),

    ACCOUNT_BLACKLIST(908,"此账号已被加入黑名单"),

    SOFT_NO_CHANGE(909,"软件不支持换绑机器"),

    REGISTER_CLOSE(910,"注册关闭"),

    TOO_FAST(911,"访问过快"),

    VERSIONS_EMPTY(912,"版本不存在"),

    KEY_EMPTY(913,"钥匙为空"),

    ACCOUNT_EMPTY(914,"用户不存在"),

    CARD_EMPTY(915,"卡密不存在"),

    CARD_USE(916,"卡密已被使用"),

    CARD_CLOSURE(917,"卡密已被封停"),

    CARD_PAST_DUE(918,"卡密已过期"),

    KEY_ERROR(919,"服务器钥匙错误"),

    ACCOUNT_ALREADY_EXIST(920,"用户名已存在"),

    PASSWORD_LENGTH_ERROR(921,"密码长度最多只能10位哟！"),

    SOFT_EMPTY(922,"软件不存在"),

    BAIDU_API_ERROR(923,"IP错误"),

    UPDATE_PASSWORD_ERROR(924,"改密失败"),

    LOGOUT_ERROR(925,"登出失败"),

    SOFT_INCONSISTENCY(926,"卡密使用绑定软件不一致"),


    //基础
    SUCCESS(10,"操作成功"),
    QUERY_SUCCESS(9,"查询成功"),
    ERROR(11,"操作失败"),
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
