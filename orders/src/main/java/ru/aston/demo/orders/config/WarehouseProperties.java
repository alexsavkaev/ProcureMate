package ru.aston.demo.orders.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "warehouse")
@Getter
@Setter
public class WarehouseProperties {
  private String url;


}
