package com.orange.verify.api.vo;

import com.orange.verify.api.bean.Card;
import lombok.Data;

@Data
public class CardVo extends Card {

    private String softId;

    private String softName;

    private Integer cardTypeUnit;

    private Long cardTypeValue;

    private String accountId;

    private String accountName;

}
