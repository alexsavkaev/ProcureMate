package ru.aston.demo.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;
import java.util.function.Supplier;

@EnableJpaRepositories
@SpringBootApplication
public class AccountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingApplication.class, args);
    }

    @Bean
    public Supplier<Instant> getCurrentTime() {
        return Instant::now;
    }

}
