package ru.aston.demo.orders.dto;

public record CreatedOrderResponseDto (
    Long id,
    String details,
    String supplier,
    Long supplierId
){

}
