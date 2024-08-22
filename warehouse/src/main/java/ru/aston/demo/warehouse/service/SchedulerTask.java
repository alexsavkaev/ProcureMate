package ru.aston.demo.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.aston.demo.warehouse.service.impl.ProductServiceImpl;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerTask {

    private final ProductServiceImpl productService;

    @Scheduled(fixedRate = 30000)
    public void scheduledTask() {
        productService.updateQuantity();
    }
}