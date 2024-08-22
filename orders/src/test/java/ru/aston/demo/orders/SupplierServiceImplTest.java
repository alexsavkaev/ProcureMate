package ru.aston.demo.orders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import ru.aston.demo.orders.config.SuppliersProperties;
import ru.aston.demo.orders.dto.ProductDto;
import ru.aston.demo.orders.dto.SupplierDto;
import ru.aston.demo.orders.entity.Product;
import ru.aston.demo.orders.entity.Supplier;
import ru.aston.demo.orders.mapper.OrderMapper;
import ru.aston.demo.orders.repository.ProductRepository;
import ru.aston.demo.orders.service.impl.SupplierServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {
  private Product createProduct() {
    return new Product(1L, "Product 1", BigDecimal.valueOf(10.99), new ArrayList<>());
  }

  private Supplier createSupplier() {
    return new Supplier(1L, "Supplier 1", new ArrayList<>(), new ArrayList<>());
  }

  private ProductDto createProductDto() {
    Product product = createProduct();
    Supplier supplier = createSupplier();
    // добавляем supplier в product
    product.getSuppliers().add(supplier);
    return new ProductDto(product.getProductId(), product.getProductName(),
        product.getProductPrice(), orderMapper.toSupplierDto(supplier));
  }

  @Mock
  private SuppliersProperties suppliersProperties;

  @Mock
  private OrderMapper orderMapper;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private RestClient restClient;

  @Mock
  private ObjectMapper mapper;

  @InjectMocks
  private SupplierServiceImpl supplierService;

//  @Test
//  public void testGetPriceList() throws Exception {
//    // Arrange
//    String responseBody = "{\"products\":[{\"id\":1,\"name\":\"Product 1\",\"price\":10.99}]}";
//    when(suppliersProperties.getUrl()).thenReturn("http://example.com");
//    when(restClient.get()).thenReturn(mock(RestClient.RequestHeadersUriSpec.class));
//    when(restClient.get().uri("/supplier-service/products/all"))
//        .thenReturn(mock(RestClient.RequestHeadersSpec.class));
//    when(restClient.get()
//        .uri("/supplier-service/products/all")
//        .retrieve().body(String.class))
//        .thenReturn(responseBody);
//    when(mapper.readValue(responseBody, any(TypeReference.class)))
//        .thenReturn(List.of(createProduct()));
//
//    // Act
//    ResponseEntity<List<ProductDto>> response = supplierService.getPriceList();
//
//    // Assert
//    assertEquals(HttpStatus.OK, response.getStatusCode());
//    assertEquals(1, Objects.requireNonNull(response.getBody()).size());
//  }
//
//  @Test
//  public void testGetPriceListWithError() throws Exception {
//    // Arrange
//    when(suppliersProperties.getUrl()).thenReturn("http://example.com");
//    when(restClient.get().uri("/supplier-service/products/all")
//        .retrieve()
//        .body(String.class))
//        .thenThrow(new Exception("Error"));
//
//    // Act and Assert
//    assertThrows(Exception.class, () -> supplierService.getPriceList());
//  }

  @Test
  public void testUpdatePrices() {
    // Arrange
    List<ProductDto> prices = List.of(createProductDto());
    when(orderMapper.toProduct(any(ProductDto.class)))
        .thenReturn(createProduct());

    // Act
    supplierService.updatePrices(prices);

    // Assert
    verify(productRepository, times(1)).saveAll(any(List.class));
  }

  @Test
  public void testGetPrices() {
    // Act and Assert
    assertNull(supplierService.getPrices());
  }
}
