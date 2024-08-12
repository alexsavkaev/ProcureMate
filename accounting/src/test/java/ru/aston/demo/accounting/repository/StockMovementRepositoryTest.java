package ru.aston.demo.accounting.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.demo.accounting.entity.StockMovementEntity;
import ru.aston.demo.accounting.type.MovementType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StockMovementRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:16.4");

    @DynamicPropertySource
    private static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }

    @Autowired
    private StockMovementRepository movementRepository;

    @Test
    void findById_thereIsNoMovement_empty() {
        // given

        // when
        Optional<StockMovementEntity> movementEntity = movementRepository.findById(1L);

        // then
        assertThat(movementEntity).isEmpty();
    }

    @Test
    void findById_thereIsMovement_present() {
        // given
        long createdId = insertRow();

        // when
        Optional<StockMovementEntity> movementEntity = movementRepository.findById(createdId);

        // then
        assertThat(movementEntity).isPresent();
    }

    @Test
    void findByMovedAtBetween_thereAreDifferentRows_byBetween() {
        // given
        insertRow(Instant.parse("2024-07-01T12:00:00Z"));
        insertRow(Instant.parse("2024-07-01T14:00:00Z"));
        insertRow(Instant.parse("2024-07-01T16:00:00Z"));
        insertRow(Instant.parse("2024-07-01T18:00:00Z"));

        // when
        List<StockMovementEntity> movementEntities = movementRepository.findByMovedAtBetween(
                Instant.parse("2024-07-01T14:00:00Z"), Instant.parse("2024-07-01T16:00:00Z"));

        // then
        assertThat(movementEntities).hasSize(2);
    }

    @Test
    void create_movement_insertToDb() {
        // given
        StockMovementEntity movementEntityToCreate = new StockMovementEntity(
                null, 10, "test", 5, BigDecimal.TEN, Instant.now(), MovementType.INCOME);

        // when
        StockMovementEntity createdEntity = movementRepository.save(movementEntityToCreate);

        // then
        Optional<StockMovementEntity> movementEntity = movementRepository.findById(createdEntity.getId());
        assertThat(movementEntity).isPresent();
    }

    @Test
    void delete_movement_deleteFromDb() {
        // given
        long createdId = insertRow();

        // when
        movementRepository.deleteById(createdId);

        // then
        Optional<StockMovementEntity> movementEntity = movementRepository.findById(createdId);
        assertThat(movementEntity).isEmpty();
    }

    private long insertRow() {
        return insertRow(Instant.now());
    }

    private long insertRow(Instant movedAt) {
        StockMovementEntity entity = new StockMovementEntity(null, 10, "test", 5, BigDecimal.TEN, movedAt, MovementType.INCOME);
        StockMovementEntity createdId = movementRepository.save(entity);
        return createdId.getId();
    }

}