package com.orange.verify.admin.rabbitmq;

import com.orange.verify.admin.config.RabbitMqConfig;
import com.orange.verify.admin.rabbitmq.bean.LeaveMessage;
import com.orange.verify.common.email.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

@Component
@Slf4j
public class EmailReceive {

    @RabbitListener(queues = RabbitMqConfig.EMAIL_SEND_CODE)
    public void sendCode(LeaveMessage leaveMessage) {

        MailUtil mailUtil = new MailUtil(leaveMessage.getSendAccount(),
                leaveMessage.getSendPassword());

        try {
            mailUtil.send(leaveMessage.getReceiveAccount(),leaveMessage.getTitle(),leaveMessage.getContent());
        } catch (MessagingException e) {
        } catch (GeneralSecurityException e) {
        }
    }

}
