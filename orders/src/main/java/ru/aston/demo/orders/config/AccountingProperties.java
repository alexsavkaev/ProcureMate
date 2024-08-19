package ru.aston.demo.orders.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "accounting")
public class AccountingProperties {
  private String url;

}
