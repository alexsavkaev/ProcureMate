package ru.aston.demo.orders.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.aston.demo.orders.config.SuppliersProperties;
import ru.aston.demo.orders.dto.ProductsPricesDto;
import ru.aston.demo.orders.repository.SupplierRepository;
import ru.aston.demo.orders.service.SupplierService;
import ru.aston.demo.orders.util.RestClientFactory;
@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

  private final SupplierRepository supplierRepository;
  private final SuppliersProperties suppliersProperties;

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public ResponseEntity<List<ProductsPricesDto>> getPriceList() {
    RestClient restClient = RestClientFactory.getRestClient(suppliersProperties.getUrl());
    try {
      String responseBody = restClient
          .get()
          .retrieve()
          .body(String.class);
      List<ProductsPricesDto> prices = mapper.readValue(responseBody, new TypeReference<>() {
      });
      return ResponseEntity.status(HttpStatus.OK).body(prices);
    } catch (JsonProcessingException e) {
      log.error("Error deserializing response", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
    } catch (Exception e) {
      log.error("Error getting price list", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
    }
  }
}
