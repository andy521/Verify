package com.orange.verify.api.vo.open;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AccountLoginVo implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 5,max = 10,message = "用户名长度是5到10位哟")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "软件id不能为空")
    private String softId;

    @NotBlank(message = "公钥不能为空")
    String publicKey;

    /**
     * 用户电脑的机器码
     * code
     */
    @NotBlank(message = "code不能为空")
    private String code;

    private String ip;

}
