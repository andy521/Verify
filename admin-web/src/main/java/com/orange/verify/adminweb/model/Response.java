package com.orange.verify.adminweb.model;

/**
 * web专用返回
 * @author Orange
 * @date 2018/10/28
 */
public class Response<T> {

    private int code;

    private String msg;

    private T data;

    private long totalTime;

    private Response(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
    }

    private Response(ResponseCode responseCode,T data) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
        this.data = data;
    }

    private Response(ResponseCode responseCode,String msg) {
        this.code = responseCode.getCode();
        this.msg = msg;
    }

    private Response(ResponseCode responseCode,String msg,T data) {
        this.code = responseCode.getCode();
        this.msg = msg;
        this.data = data;
    }

    public static Response success() {
        return new Response(ResponseCode.SUCCESS);
    }
    public static Response error() {
        return new Response(ResponseCode.ERROR);
    }
    public static Response build(ResponseCode responseCode) {
        return new Response(responseCode);
    }
    public static<T> Response<T> build(ResponseCode responseCode,T data) {
        return new Response(responseCode,data);
    }
    public static Response build(ResponseCode responseCode,String msg) {
        return new Response(responseCode,msg);
    }
    public static<T> Response<T> build(ResponseCode responseCode,String msg,T data) {
        return new Response(responseCode,msg,data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
