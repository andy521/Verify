package com.orange.verify.api.vo;

import com.orange.verify.api.bean.Account;
import lombok.Data;

@Data
public class AccountVo extends Account {

    private String softId;

    private String softName;

}
