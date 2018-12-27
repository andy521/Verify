package com.orange.verify.admin.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EMAIL_SEND_CODE = "email_send_code";

    @Bean
    public Queue queue() {
        return new Queue(EMAIL_SEND_CODE);
    }

}
