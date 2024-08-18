package ru.aston.demo.orders;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import ru.aston.demo.orders.config.AccountingProperties;
import ru.aston.demo.orders.config.SuppliersProperties;
import ru.aston.demo.orders.config.WarehouseProperties;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableConfigurationProperties({
    WarehouseProperties.class, SuppliersProperties.class, AccountingProperties.class
})
public class OrdersApplication {



    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

}
