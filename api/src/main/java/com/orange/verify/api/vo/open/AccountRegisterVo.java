package com.orange.verify.api.vo.open;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AccountRegisterVo implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 1,max = 10,message = "用户名长度是10位哟")
    private String username;

    @NotBlank(message = "QQ号不能为空")
    @Size(min = 1,max = 10,message = "QQ号长度是10位哟")
    private String qq;

    @NotBlank(message = "密码不能为空")
    @Size(min = 1,max = 10,message = "密码长度是10位哟")
    private String password;

    @NotBlank(message = "软件id不能为空")
    private String softId;

    /**
     * 用户的真实姓名
     * name
     */
    @NotBlank(message = "名字不能为空")
    @Size(min = 1,max = 10,message = "真实姓名长度是10位哟")
    private String name;

    /**
     * 用户电脑的机器码
     * code
     */
    @NotBlank(message = "机器码不能为空")
    private String code;

    private String ip;

    @NotBlank(message = "公钥不能为空")
    String publicKey;

}
