package ru.aston.demo.orders.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "suppliers")
@Getter
@Setter
public class SuppliersProperties {
  private String url;

}
