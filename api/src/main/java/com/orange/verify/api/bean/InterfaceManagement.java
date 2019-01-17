package com.orange.verify.api.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口默认控制配置
 * visit 1=可以访问 0=接口关闭
 * ipHandle 1开启ip限流控制 0关闭
 * ipVisits ip访问次数，限制接口在 xx 分钟内容只能访问 xx 次，默认60次
 * ipRedisInterval ip redis缓存多少分钟 默认1分钟
 */
@Data
public class InterfaceManagement implements Serializable {

    private String key;

    private String remarks;

    private Integer visit;

    private Integer ipHandle;

    private Long ipVisits;

    private Long ipRedisInterval;

}
