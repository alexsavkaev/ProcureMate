package ru.aston.demo.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import ru.aston.demo.orders.dto.ProductsPricesDto;

public interface SupplierService {
  ResponseEntity<List<ProductsPricesDto>> getPriceList() throws JsonProcessingException;


}
