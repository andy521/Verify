package com.orange.verify.api.vo;

import com.orange.verify.api.bean.Soft;

public class SoftVo extends Soft {

    //软件用户数量
    private Long accountTotal;

    //最新版本
    private String versionsNum;

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
