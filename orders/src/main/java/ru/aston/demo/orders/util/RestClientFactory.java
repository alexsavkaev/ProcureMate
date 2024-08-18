package ru.aston.demo.orders.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientFactory {
  public static RestClient getRestClient(String serviceUrl){
    if(serviceUrl.startsWith("http://suppliers")){
      return RestClient.builder()
          .baseUrl("http://suppliers:8080")
          .build();
    } else if (serviceUrl.startsWith("http://warehouse")) {
      return RestClient.builder()
          .baseUrl("http://warehouse:8081")
          .build();
    } else if (serviceUrl.startsWith("http://accounting")) {
      return RestClient.builder()
          .baseUrl("http://accounting:8082")
          .build();

    } else throw new UnsupportedOperationException("No such service found");
  }

}
