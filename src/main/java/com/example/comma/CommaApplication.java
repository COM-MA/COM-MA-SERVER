package com.example.comma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CommaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommaApplication.class, args);
    }

}
