package ru.aston.demo.orders.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.aston.demo.orders.config.SuppliersProperties;
import ru.aston.demo.orders.dto.ProductDto;
import ru.aston.demo.orders.entity.Product;
import ru.aston.demo.orders.mapper.OrderMapper;
import ru.aston.demo.orders.repository.ProductRepository;
import ru.aston.demo.orders.service.SupplierService;
import ru.aston.demo.orders.util.RestClientFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

  private final SuppliersProperties suppliersProperties;
  private final OrderMapper orderMapper;
  private final ProductRepository productRepository;

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public ResponseEntity<List<ProductDto>> getPriceList() {
    RestClient restClient = RestClientFactory.getRestClient(suppliersProperties.getUrl());
    try {
      String responseBody = restClient
          .get()
          .uri("/supplier-service/products/all")
          .retrieve()
          .body(String.class);
      List<ProductDto> prices = mapper.readValue(responseBody, new TypeReference<>() {
      });
      updatePrices(prices);
      return ResponseEntity.status(HttpStatus.OK).body(prices);
    } catch (JsonProcessingException e) {
      log.error("Error deserializing response", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
    } catch (Exception e) {
      log.error("Error getting price list", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
    }
  }

  public void updatePrices(List<ProductDto> prices) {
    log.info("Starting updatePrices method with {} prices", prices.size());

    List<Product> products = new ArrayList<>();
    log.debug("Created empty list of products");

    Product product;
    for (ProductDto productDto : prices) {
      log.debug("Processing productDto: {}", productDto);
      product = orderMapper.toProduct(productDto);
      log.debug("Mapped productDto to product: {}", product);
      log.debug("Product: {}", product.toString());
      products.add(product);
    }

    log.info("Saving {} products to database", products.size());
    productRepository.saveAll(products);
    log.info("Products saved successfully");
  }

  @Override
  public ResponseEntity<List<ProductDto>> getPrices() {
    return null;
  }
}
