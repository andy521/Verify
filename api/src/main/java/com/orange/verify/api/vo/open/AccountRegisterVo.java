package com.orange.verify.api.vo.open;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class AccountRegisterVo implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 5,max = 10,message = "用户名长度是5到10位哟")
    private String username;

    @NotBlank(message = "QQ号不能为空")
    @Size(min = 1,max = 10,message = "QQ号长度是1到10位哟")
    private String qq;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "软件id不能为空")
    private String softId;

    @NotBlank(message = "安全码不能为空")
    @Size(min = 5,max = 10,message = "安全码长度是5到10位哟")
    private String securityCode;

    /**
     * 用户电脑的机器码
     * code
     */
    @NotBlank(message = "code不能为空")
    private String code;
    
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Size(min = 6,max = 6,message = "验证码长度是6位哟")
    private String vc;

    /**
     * 用户的真实姓名
     * name
     */
    @NotBlank(message = "名字不能为空")
    @Size(min = 1,max = 10,message = "真实姓名长度是1到10位哟")
    private String name;

    private String ip;

    @NotBlank(message = "公钥不能为空")
    String publicKey;

}
