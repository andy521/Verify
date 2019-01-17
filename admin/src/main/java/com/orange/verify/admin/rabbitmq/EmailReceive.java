package com.orange.verify.admin.rabbitmq;

import com.orange.verify.admin.config.RabbitMqConfig;
import com.orange.verify.admin.rabbitmq.bean.LeaveMessage;
import com.orange.verify.common.email.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

@Component
public class EmailReceive {

    private static final Logger log = LoggerFactory.getLogger(EmailReceive.class);

    @RabbitListener(queues = RabbitMqConfig.EMAIL_SEND_CODE)
    public void sendCode(LeaveMessage leaveMessage) {

        log.info("发送邮件啦~~");

        MailUtil mailUtil = new MailUtil(leaveMessage.getSendAccount(),
                leaveMessage.getSendPassword());

        try {
            mailUtil.send(leaveMessage.getReceiveAccount(),leaveMessage.getTitle(),leaveMessage.getContent());
            log.info("邮件发送成功");
        } catch (MessagingException e) {
            log.error("邮件错误：" + e.getMessage());
        } catch (GeneralSecurityException e) {
            log.error("邮件错误：" + e.getMessage());
        }
    }

}
