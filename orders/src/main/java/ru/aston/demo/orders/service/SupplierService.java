package ru.aston.demo.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import ru.aston.demo.orders.dto.ProductDto;

public interface SupplierService {
  ResponseEntity<List<ProductDto>> getPriceList() throws JsonProcessingException;


  ResponseEntity<List<ProductDto>> getPrices();
}
