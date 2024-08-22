package ru.aston.demo.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.aston.demo.accounting.dto.OrderDto;
import ru.aston.demo.accounting.dto.StockMovementDto;
import ru.aston.demo.accounting.mapper.OrderMovementMapper;
import ru.aston.demo.accounting.mapper.StockMovementDtoMapper;
import ru.aston.demo.accounting.model.StockMovement;
import ru.aston.demo.accounting.service.StockMovementService;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
public class StockMovementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockMovementController.class);


    private final StockMovementService movementService;
    private final StockMovementDtoMapper movementDtoMapper;
    private final Supplier<Instant> getCurrentTime;

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementDto> findById(@PathVariable("id") long id) {
        return movementService.findById(id)
                .map(movement -> ResponseEntity.ok(movementDtoMapper.toStockMovementDto(movement)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<StockMovementDto>> findAll(
            @RequestParam(value = "moved_at_from", required = false) Instant movedAtFrom,
            @RequestParam(value = "moved_at_to", required = false) Instant movedAtTo) {

        movedAtFrom = getOrDefault(movedAtFrom, getCurrentTime.get().minus(30, DAYS));
        movedAtTo = getOrDefault(movedAtTo, getCurrentTime.get());

        List<StockMovementDto> movementDtos = movementService.findByMovedAtBetween(movedAtFrom, movedAtTo)
                .stream()
                .map(movementDtoMapper::toStockMovementDto)
                .toList();

        return ResponseEntity.ok(movementDtos);
    }

    @PostMapping
    public ResponseEntity<StockMovementDto> create(
        @RequestBody StockMovementDto movementDto, UriComponentsBuilder uriBuilder) {

        LOGGER.info("Creating new stock movement");
        LOGGER.debug("Received movement DTO: {}", movementDto);

        StockMovement movement = movementDtoMapper.toStockMovement(movementDto);
        LOGGER.debug("Mapped movement DTO to entity: {}", movement);

        long createdId = movementService.create(movement);
        LOGGER.info("Created new stock movement with ID: {}", createdId);

        URI uri = uriBuilder.path("/movements/{id}").build(createdId);
        LOGGER.debug("Built URI for created movement: {}", uri);

        LOGGER.info("Returning created response");
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/order")
    public ResponseEntity<List<StockMovementDto>> create(@RequestBody OrderDto orderDto, UriComponentsBuilder uriBuilder) {
        List<StockMovement> movements = OrderMovementMapper.toStockMovementDto(orderDto);
        List<Long> ids = movementService.create(movements);
        URI uri = uriBuilder.path("/movements/{id}").build(ids.getLast());

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        movementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Instant getOrDefault(Instant instant, Instant defaultValue) {
        return Optional.ofNullable(instant).orElse(defaultValue);
    }
}
