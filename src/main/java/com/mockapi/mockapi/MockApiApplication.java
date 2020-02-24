package com.mockapi.mockapi;

import com.mockapi.mockapi.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MockApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockApiApplication.class, args);
    }

}
