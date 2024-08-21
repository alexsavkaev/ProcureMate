package ru.aston.demo.accounting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.demo.accounting.entity.StockMovementEntity;
import ru.aston.demo.accounting.mapper.StockMovementMapper;
import ru.aston.demo.accounting.model.StockMovement;
import ru.aston.demo.accounting.repository.StockMovementRepository;
import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StockMovementServiceTest {

    private static final StockMovementEntity MOVEMENT_ENTITY = movementEntity(1);
    private static final StockMovement MOVEMENT = movement(1);

    private static final Instant MOVED_AT_FROM = Instant.parse("2024-07-01T12:00:00Z");
    private static final Instant MOVED_AT_TO = Instant.parse("2024-07-31T12:00:00Z");

    @Mock
    private StockMovementRepository movementRepository;

    @Mock
    private StockMovementMapper movementMapper;

    private StockMovementService movementService;

    @BeforeEach
    void setUp() {
        movementService = new StockMovementService(movementRepository, movementMapper);
    }

    @Test
    void findById_movementExists_fromRepository() {
        // given
        given(movementRepository.findById(1L)).willReturn(Optional.of(MOVEMENT_ENTITY));
        given(movementMapper.toStockMovement(MOVEMENT_ENTITY)).willReturn(MOVEMENT);

        // when
        Optional<StockMovement> movement = movementService.findById(1L);

        //then
        assertThat(movement).hasValue(MOVEMENT);
    }

    @Test
    void findById_movementNotExists_empty() {
        // given
        given(movementRepository.findById(1L)).willReturn(Optional.empty());

        // when
        Optional<StockMovement> stockMovement = movementService.findById(1L);

        //then
        assertThat(stockMovement).isEmpty();
    }

    @Test
    void findByMovedAtBetween_movedAtPassed_delegateToRepository() {
        // given
        StockMovementEntity movementEntity1 = movementEntity(1);
        StockMovementEntity movementEntity2 = movementEntity(2);
        StockMovement movement1 = movement(1);
        StockMovement movement2 = movement(2);

        given(movementRepository.findByMovedAtBetween(MOVED_AT_FROM, MOVED_AT_TO))
                .willReturn(List.of(movementEntity1, movementEntity2));
        given(movementMapper.toStockMovement(movementEntity1)).willReturn(movement1);
        given(movementMapper.toStockMovement(movementEntity2)).willReturn(movement2);

        // when
        List<StockMovement> movements = movementService.findByMovedAtBetween(MOVED_AT_FROM, MOVED_AT_TO);

        //then
        assertThat(movements).containsOnly(movement1, movement2);
    }

    @Test
    void create_movement_delegateToRepository() {
        // given
        StockMovementEntity createdMovementEntity = movementEntity(123);
        given(movementMapper.toStockMovementEntity(MOVEMENT)).willReturn(MOVEMENT_ENTITY);
        given(movementRepository.save(MOVEMENT_ENTITY)).willReturn(createdMovementEntity);

        //when
        long createdId = movementService.create(MOVEMENT);

        //then
        assertThat(createdId).isEqualTo(123L);
    }

    @Test
    void delete_movementId_delegateToRepository() {
        // given
        willDoNothing().given(movementRepository).deleteById(anyLong());

        //when
        movementService.delete(123);

        //then
        then(movementRepository).should().deleteById(123L);
    }

    private static StockMovementEntity movementEntity(long id) {
        StockMovementEntity entity = new StockMovementEntity();
        entity.setId(id);
        return entity;
    }

    private static StockMovement movement(long id) {
        return new StockMovement(id, 10, "test", 3, BigDecimal.TEN, Instant.now(), MovementType.INCOME);
    }
}