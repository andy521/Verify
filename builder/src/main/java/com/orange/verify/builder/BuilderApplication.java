package com.orange.verify.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * 根据填写的api bean 生成到数据库
 * @author orange
 */
@SpringBootApplication
@EntityScan(basePackages={"${entity.scan.base.packages}"})
public class BuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuilderApplication.class, args);
    }
}
