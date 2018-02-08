package com.mt.blockchain.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mt.blockchain.*")
public class Application {

    private static SpringApplication springApplication = new SpringApplication(Application.class);

    public static void main(String[] args) {
        springApplication.run(args);
    }

}
