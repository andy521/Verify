package com.orange.verify.adminweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableFeignClients(value = "com.orange.verify.api.face")
//@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
@EnableSwagger2
public class AdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminWebApplication.class, args);
    }
}
