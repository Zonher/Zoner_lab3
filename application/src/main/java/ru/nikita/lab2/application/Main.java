package ru.nikita.lab2.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "ru.nikita.lab2")
@EntityScan(basePackages = "ru.nikita.lab2.dao.entity")
@EnableJpaRepositories(basePackages = "ru.nikita.lab2.dao.repository")
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }
}
