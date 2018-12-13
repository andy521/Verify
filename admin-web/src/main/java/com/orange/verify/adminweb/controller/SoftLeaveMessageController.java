package com.orange.verify.adminweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.api.service.SoftLeaveMessageService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(description = "软件留言")
@Controller
@RequestMapping(value = "soft_leave_message")
public class SoftLeaveMessageController {

    @Reference
    private SoftLeaveMessageService softLeaveMessageService;

}
