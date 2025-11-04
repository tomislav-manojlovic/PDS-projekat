package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableFeignClients(basePackages = "com.example.feign")
public class MainOrdersService {
    public static void main(String[] args) {
        SpringApplication.run(MainOrdersService.class, args);
    }
}