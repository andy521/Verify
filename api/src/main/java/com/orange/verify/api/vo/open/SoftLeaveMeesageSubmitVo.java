package com.orange.verify.api.vo.open;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SoftLeaveMeesageSubmitVo implements Serializable {

    @NotBlank(message = "留言内容不能为空")
    @Size(min = 1,max = 255,message = "留言内容长度是255位哟")
    private String content;

    @NotBlank(message = "留言QQ不能为空")
    @Size(min = 1,max = 10,message = "留言QQ长度是10位哟")
    private String qq;

    @NotBlank(message = "软件id不能为空")
    private String softId;

    private String ip;

}
