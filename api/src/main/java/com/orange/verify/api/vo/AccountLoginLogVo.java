package com.orange.verify.api.vo;

import com.orange.verify.api.bean.AccountLoginLog;
import lombok.Data;

@Data
public class AccountLoginLogVo extends AccountLoginLog {

    private String softName;

    private String accountName;

}
