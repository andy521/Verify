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
    UNKNOWN_ERROR(901,"不好意思，发生了未知错误！"),

    PARAMETER_ERROR(902,"提交参数错误，请仔细检查提交参数！"),

    EMPTY(903,"无"),

    PRODUCTION_TOO_MUCH(904,"生产过多充值卡密"),

    REGISTER_ERROR(905,"注册失败"),

    LOGIN_ERROR(906,"本软件不支持换机器进行使用"),

    SOFT_CLOSE(907,"软件关闭开放使用"),

    ACCOUNT_BLACKLIST(908,"此账号已被加入黑名单！"),

    SOFT_NO_CHANGE(909,"软件不支持换绑机器"),

    REGISTER_CLOSE(910,"注册关闭"),

    TOO_FAST(911,"访问过快"),

    VERSIONS_EMPTY(912,"版本不存在"),

    KEY_EMPTY(913,"钥匙为空，请重启软件重试！"),

    ACCOUNT_EMPTY(914,"用户不存在"),

    CARD_EMPTY(915,"卡密不存在"),

    CARD_USE(916,"卡密已被使用"),

    CARD_CLOSURE(917,"卡密已被封停"),

    CARD_PAST_DUE(918,"卡密已过期"),

    KEY_ERROR(919,"服务器钥匙错误"),

    ACCOUNT_ALREADY_EXIST(920,"用户名已存在"),

    PASSWORD_LENGTH_ERROR(921,"密码长度是5到10位哟！"),

    SOFT_EMPTY(922,"软件不存在"),

    BAIDU_API_ERROR(923,"IP错误"),

    UPDATE_PASSWORD_ERROR(924,"改密失败"),

    LOGOUT_ERROR(925,"登出失败"),

    SOFT_INCONSISTENCY(926,"卡密使用绑定软件不一致"),

    ACCOUNT_NOT_BOUND_CARD(927,"账号还未绑定卡密，请先充值再使用！"),

    SECURITY_CODE_ERROR(928,"安全码错误！"),

    BINDING_CARD_ERROR(929,"绑定卡密失败"),

    PASSWORD_ERROR(930,"密码错误"),

    BINDING_CODE_ERROR(931,"绑定机器失败"),

    LEAVE_MESSAGE_SEND_ERROR(932,"留言发送失败"),

    VC_EMPTY(933,"验证码过期了，请重新获取验证码"),

    VC_MISMATCHES(934,"验证码输入错误"),

    NIMIETY(935,"当前注册人数过多，稍后再试！"),

    SERVER_ERROR(936,"服务器错误"),

    SOFT_FREE(937,"软件是免费的，无需绑定卡密"),

    INTERFACE_CLOSE(938,"访问失败，接口已被关闭！"),

    //基础
    QUERY_SUCCESS(9,"查询成功"),
    SUCCESS(10,"操作成功"),
    ERROR(11,"操作失败"),
    NOT_LOGIN(12,"未登录"),
    QUERY_ERROR(13,"查询失败"),
    NOT_ROLE(99,"无角色");

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
