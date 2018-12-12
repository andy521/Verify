package com.orange.verify.api.vo;

import com.orange.verify.api.bean.SoftVersions;

public class SoftVersionsVo extends SoftVersions {

    private String softName;

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }
}
