package ru.aston.demo.orders.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aston.demo.orders.dto.ProductsPricesDto;
import ru.aston.demo.orders.service.impl.SupplierServiceImpl;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {
  private final SupplierServiceImpl supplierService;

  @GetMapping
  public ResponseEntity<List<ProductsPricesDto>> getPrices(){
    return supplierService.getPriceList();
  }

}

