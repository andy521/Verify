package com.orange.verify.api.vo.open;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AccountUpdatePasswordVo implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 5,max = 10,message = "用户名长度是5到10位哟")
    private String username;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 5,max = 10,message = "新密码长度是5到10位哟")
    private String password;

    @NotBlank(message = "安全码不能为空")
    @Size(min = 5,max = 10,message = "安全码长度是5到10位哟")
    private String securityCode;

    @NotBlank(message = "软件id不能为空")
    private String softId;

}
