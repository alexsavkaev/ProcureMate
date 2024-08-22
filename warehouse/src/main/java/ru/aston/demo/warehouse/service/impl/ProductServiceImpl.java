package ru.aston.demo.warehouse.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.aston.demo.warehouse.dto.ProductsDto;
import ru.aston.demo.warehouse.dto.StockMovementDto;
import ru.aston.demo.warehouse.mapper.ProductsMapper;
import ru.aston.demo.warehouse.model.MovementType;

import ru.aston.demo.warehouse.model.Product;
import ru.aston.demo.warehouse.repository.ProductRepository;
import ru.aston.demo.warehouse.service.ProductsService;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductsService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductsMapper productsMapper;
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public ProductsDto findById(Long id) {
        Product product = productRepository.findById(id).get();
        return productsMapper.toProductDto(product);
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public ProductsDto createProduct(ProductsDto productsDto) {
        if (productsDto != null) {
            Product product = productRepository.save(productsMapper.toProducts(productsDto));
            return productsMapper.toProductDto(product);
        } else {
            return null;
        }
    }

//    @Override
//    public ProductsDto updateProduct(ProductsDto productsDto, Long id) {
//        Product productsUpdate = productRepository.findById(id).get();
//        if (productsUpdate != null) {
//            if (productsDto.productInfo() != null) {
//                productsUpdate.setProductInfo(productsDto.productInfo());
//            }
//            if (productsDto.productName() != null) {
//                productsUpdate.setProductName(productsDto.productName());
//            }
//            if (productsDto.productPrice() != null) {
//                productsUpdate.setProductPrice(productsDto.productPrice());
//            }
//            if (productsDto.productQuantity() != 0) {
//                productsUpdate.setProductQuantity(productsDto.productQuantity());
//            }
//            productsUpdate = productRepository.save(productsUpdate);
//            return productsMapper.toProductDto(productsUpdate);
//        }
//        return null;
//    }

    public List<Product> updateQuantity() {
        List<Product> updatedQuantities = productRepository.findAll();

        for (Product product : updatedQuantities) {
            int newQuantity = getDelta(product.getProductQuantity());
            if ((newQuantity - product.getProductQuantity() > 0)) {
                if (product.getProductQuantity() > newQuantity) {
                    product.setMovementType(MovementType.OUTCOME);
                } else {
                    product.setMovementType(MovementType.INCOME);
                }
                product.setProductQuantity(newQuantity);

            }
        }
        RestClient restClient = RestClient.create();
        try {
            String requestBody = mapper.writeValueAsString(updatedQuantities);// сериализуйте данные, которые вы хотите передать в теле запроса
                String responseBody = restClient
                .post()
                .uri("http://accounting:8082/movements")
                .body(requestBody)
                .retrieve()
                .body(String.class);
            List<StockMovementDto> stockMovementDtos = mapper.readValue(responseBody, new TypeReference<>() {
            });
            return productRepository.saveAll(updatedQuantities);
        } catch (JsonProcessingException e) {
            // обработайте ошибку десериализации
        } catch (Exception e) {
            // обработайте другие ошибки
        }

        return productRepository.saveAll(updatedQuantities);
    }


        @Override
        public ProductsDto deleteProduct (Long id){
            Product product = productRepository.findById(id).get();
            productRepository.delete(product);
          return productsMapper.toProductDto(product);
        }

        private int getDelta ( int originalNumber){
            double randomNumber = Math.random();
            double newQuantity;

            if (randomNumber < 0.5) {
                newQuantity = originalNumber - (originalNumber * 0.5);

            } else {
                newQuantity = originalNumber + (originalNumber * 0.5);
            }
            return (int) newQuantity;
        }
    }




