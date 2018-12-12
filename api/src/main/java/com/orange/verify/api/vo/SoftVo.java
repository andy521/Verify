package com.orange.verify.api.vo;

import java.io.Serializable;

public class SoftVo implements Serializable {

    //软件名称
    private String name;

    //软件id
    private String id;

    //状态
    private Integer serviceStatus;

    //软件用户数量
    private Long accountTotal;

    //最新版本
    private String versionsNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Long getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(Long accountTotal) {
        this.accountTotal = accountTotal;
    }

    public String getVersionsNum() {
        return versionsNum;
    }

    public void setVersionsNum(String versionsNum) {
        this.versionsNum = versionsNum;
    }
}
