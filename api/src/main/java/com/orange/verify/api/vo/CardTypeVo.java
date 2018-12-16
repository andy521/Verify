package com.orange.verify.api.vo;

import com.orange.verify.api.bean.CardType;

public class CardTypeVo extends CardType {

    private String softName;

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }
}
