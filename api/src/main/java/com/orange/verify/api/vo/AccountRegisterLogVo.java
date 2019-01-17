package com.orange.verify.api.vo;

import com.orange.verify.api.bean.AccountRegisterLog;
import lombok.Data;

@Data
public class AccountRegisterLogVo extends AccountRegisterLog {

    private String softName;

    private String accountName;

}
